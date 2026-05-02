using System;

namespace MessageConsumer.Models
{
    public class MessageRecord
    {
        public int Id { get; set; }
        public string MessageType { get; set; } = string.Empty;
        public string Content { get; set; } = string.Empty;
        public DateTime ReceivedAt { get; set; }
    }
}
