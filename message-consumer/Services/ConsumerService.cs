using Microsoft.Extensions.Hosting;
using MessageConsumer.Data;
using MessageConsumer.Models;
using Microsoft.Extensions.DependencyInjection;

namespace MessageConsumer.Services
{
    public class ConsumerService : BackgroundService
    {
        private readonly IQueueClient _queue;
        private readonly IServiceProvider _services;

        public ConsumerService(IQueueClient queue, IServiceProvider services)
        {
            _queue = queue;
            _services = services;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            while (!stoppingToken.IsCancellationRequested)
            {
                try
                {
                    var msg = await _queue.ReceiveMessageAsync(stoppingToken);
                    if (msg is not null)
                    {
                        using var scope = _services.CreateScope();
                        var db = scope.ServiceProvider.GetRequiredService<AppDbContext>();
                        db.Messages.Add(new MessageRecord
                        {
                            MessageType = msg.Type,
                            Content = msg.Content,
                            ReceivedAt = DateTime.UtcNow
                        });
                        await db.SaveChangesAsync(stoppingToken);
                    }
                    else
                    {
                        await Task.Delay(500, stoppingToken);
                    }
                }
                catch (OperationCanceledException) { break; }
                catch { await Task.Delay(1000, stoppingToken); }
            }
        }
    }
}
