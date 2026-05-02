using Microsoft.EntityFrameworkCore;
using MessageConsumer.Data;
using MessageConsumer.Models;
using MessageConsumer.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(opt =>
    opt.UseSqlite("Data Source=messages.db"));
builder.Services.AddSingleton<IQueueClient, InMemoryQueueClient>();
builder.Services.AddHostedService<ConsumerService>();

var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<AppDbContext>();
    db.Database.EnsureCreated();
}

app.MapGet("/messages", async (AppDbContext db, string? type) =>
{
    if (string.IsNullOrEmpty(type))
        return Results.Ok(await db.Messages.OrderByDescending(m => m.ReceivedAt).ToListAsync());
    return Results.Ok(await db.Messages.Where(m => m.MessageType == type).OrderByDescending(m => m.ReceivedAt).ToListAsync());
});

app.MapGet("/messages/{id}", async (int id, AppDbContext db) =>
{
    var m = await db.Messages.FindAsync(id);
    return m is null ? Results.NotFound() : Results.Ok(m);
});

app.Run();
