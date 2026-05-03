using System.Text;
using System.Text.Json;
using RabbitMQ.Client;

namespace MessageConsumer.Services
{
    public class RabbitMqQueueClient : IQueueClient
    {
        private const string QueueName = "taller3.messages";

        private readonly SemaphoreSlim _lock = new(1, 1);
        private IConnection? _connection;
        private IChannel? _channel;

        private static readonly JsonSerializerOptions JsonOptions = new()
        {
            PropertyNameCaseInsensitive = true
        };

        private async Task<IChannel> GetChannelAsync(CancellationToken cancellationToken)
        {
            if (_channel is not null && _channel.IsOpen)
            {
                return _channel;
            }

            await _lock.WaitAsync(cancellationToken);

            try
            {
                if (_channel is not null && _channel.IsOpen)
                {
                    return _channel;
                }

                var factory = new ConnectionFactory
                {
                    HostName = "localhost",
                    Port = 5672,
                    UserName = "guest",
                    Password = "guest",
                    ClientProvidedName = "Taller3 Message Consumer"
                };

                _connection = await factory.CreateConnectionAsync(cancellationToken);
                _channel = await _connection.CreateChannelAsync(cancellationToken: cancellationToken);

                await _channel.QueueDeclareAsync(
                    queue: QueueName,
                    durable: true,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null,
                    cancellationToken: cancellationToken
                );

                return _channel;
            }
            finally
            {
                _lock.Release();
            }
        }

        public async Task SendMessageAsync(QueueMessage message, CancellationToken cancellationToken)
        {
            var channel = await GetChannelAsync(cancellationToken);

            var json = JsonSerializer.Serialize(message, JsonOptions);
            var body = Encoding.UTF8.GetBytes(json);

            var properties = new BasicProperties
            {
                Persistent = true
            };

            await channel.BasicPublishAsync(
                exchange: string.Empty,
                routingKey: QueueName,
                mandatory: true,
                basicProperties: properties,
                body: body,
                cancellationToken: cancellationToken
            );
        }

        public async Task<QueueMessage?> ReceiveMessageAsync(CancellationToken cancellationToken)
        {
            var channel = await GetChannelAsync(cancellationToken);

            var result = await channel.BasicGetAsync(
                queue: QueueName,
                autoAck: false,
                cancellationToken: cancellationToken
            );

            if (result is null)
            {
                return null;
            }

            try
            {
                var json = Encoding.UTF8.GetString(result.Body.Span);
                var message = JsonSerializer.Deserialize<QueueMessage>(json, JsonOptions);

                await channel.BasicAckAsync(
                    deliveryTag: result.DeliveryTag,
                    multiple: false,
                    cancellationToken: cancellationToken
                );

                return message;
            }
            catch
            {
                await channel.BasicNackAsync(
                    deliveryTag: result.DeliveryTag,
                    multiple: false,
                    requeue: false,
                    cancellationToken: cancellationToken
                );

                throw;
            }
        }

        public async Task<int> SeedSampleMessagesAsync(CancellationToken cancellationToken)
        {
            var sampleMessages = new[]
            {
                new QueueMessage("info", "Usuario inscrito al curso A"),
                new QueueMessage("task", "Entrega tarea 1 tarde"),
                new QueueMessage("grade", "Calificacion: 4.5")
            };

            foreach (var message in sampleMessages)
            {
                await SendMessageAsync(message, cancellationToken);
            }

            return sampleMessages.Length;
        }
    }
}