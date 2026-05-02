using System.Threading;
using System.Threading.Tasks;

namespace MessageConsumer.Services
{
    public record QueueMessage(string Type, string Content);

    public interface IQueueClient
    {
        Task<QueueMessage?> ReceiveMessageAsync(CancellationToken cancellationToken);

        Task SendMessageAsync(QueueMessage message, CancellationToken cancellationToken);

        Task<int> SeedSampleMessagesAsync(CancellationToken cancellationToken);
    }
}