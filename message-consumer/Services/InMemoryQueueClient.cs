using System.Collections.Concurrent;
using System.Threading;
using System.Threading.Tasks;

namespace MessageConsumer.Services
{
    public class InMemoryQueueClient : IQueueClient
    {
        private readonly ConcurrentQueue<QueueMessage> _queue = new();

        public Task<QueueMessage?> ReceiveMessageAsync(CancellationToken cancellationToken)
        {
            if (_queue.TryDequeue(out var msg))
            {
                return Task.FromResult<QueueMessage?>(msg);
            }

            return Task.FromResult<QueueMessage?>(null);
        }

        public Task SendMessageAsync(QueueMessage message, CancellationToken cancellationToken)
        {
            _queue.Enqueue(message);
            return Task.CompletedTask;
        }

        public Task<int> SeedSampleMessagesAsync(CancellationToken cancellationToken)
        {
            var sampleMessages = new[]
            {
                new QueueMessage("info", "Usuario inscrito al curso A"),
                new QueueMessage("task", "Entrega tarea 1 tarde"),
                new QueueMessage("grade", "Calificacion: 4.5")
            };

            foreach (var message in sampleMessages)
            {
                _queue.Enqueue(message);
            }

            return Task.FromResult(sampleMessages.Length);
        }
    }
}