using Microsoft.EntityFrameworkCore;
using MessageConsumer.Models;

namespace MessageConsumer.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<MessageRecord> Messages { get; set; } = null!;
    }
}
