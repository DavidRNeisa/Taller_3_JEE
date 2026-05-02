using System.Collections.Concurrent;
using System.Threading;
using System.Threading.Tasks;

namespace MessageConsumer.Services
{
    public class InMemoryQueueClient : IQueueClient
    {
        private readonly ConcurrentQueue<QueueMessage> _q = new();

        public InMemoryQueueClient()
        {
            // sample messages
            _q.Enqueue(new QueueMessage("info", "Usuario inscrito al curso A"));
            _q.Enqueue(new QueueMessage("task", "Entrega tarea 1 tarde"));
            _q.Enqueue(new QueueMessage("grade", "Calificacion: 4.5"));
        }

        public Task<QueueMessage?> ReceiveMessageAsync(CancellationToken cancellationToken)
        {
            if (_q.TryDequeue(out var msg))
                return Task.FromResult<QueueMessage?>(msg);

            // no message: wait a bit and return null
            return Task.FromResult<QueueMessage?>(null);
        }
    }
}
