using Microsoft.EntityFrameworkCore;
using MessageConsumer.Data;
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

// Envía un mensaje a la cola desde terminal.
// Ejemplo:
// POST /queue/publish
app.MapPost("/queue/publish", async (
    PublishMessageRequest request,
    IQueueClient queue,
    CancellationToken cancellationToken) =>
{
    if (string.IsNullOrWhiteSpace(request.Type))
    {
        return Results.BadRequest(new { error = "El tipo del mensaje es obligatorio." });
    }

    if (string.IsNullOrWhiteSpace(request.Content))
    {
        return Results.BadRequest(new { error = "El contenido del mensaje es obligatorio." });
    }

    var message = new QueueMessage(
        request.Type.Trim().ToLower(),
        request.Content.Trim()
    );

    await queue.SendMessageAsync(message, cancellationToken);

    return Results.Ok(new
    {
        status = "Mensaje enviado a la cola",
        messageType = message.Type,
        content = message.Content
    });
});

// Carga mensajes de prueba a la cola.
// Ejemplo:
// POST /queue/seed
app.MapPost("/queue/seed", async (
    IQueueClient queue,
    CancellationToken cancellationToken) =>
{
    var count = await queue.SeedSampleMessagesAsync(cancellationToken);

    return Results.Ok(new
    {
        status = "Mensajes de prueba enviados a la cola",
        count
    });
});

// Consulta todos los mensajes o filtra por tipo.
// Ejemplo:
// GET /messages
// GET /messages?type=task
app.MapGet("/messages", async (AppDbContext db, string? type) =>
{
    if (string.IsNullOrWhiteSpace(type))
    {
        var messages = await db.Messages
            .OrderByDescending(m => m.ReceivedAt)
            .ToListAsync();

        return Results.Ok(messages);
    }

    var filteredMessages = await db.Messages
        .Where(m => m.MessageType.ToLower() == type.ToLower())
        .OrderByDescending(m => m.ReceivedAt)
        .ToListAsync();

    return Results.Ok(filteredMessages);
});

// Estadísticas de mensajes por tipo.
// Ejemplo:
// GET /messages/stats
app.MapGet("/messages/stats", async (AppDbContext db) =>
{
    var totalMessages = await db.Messages.CountAsync();

    var statsByType = await db.Messages
        .GroupBy(m => m.MessageType)
        .Select(g => new
        {
            messageType = g.Key,
            count = g.Count(),
            firstReceivedAt = g.Min(m => m.ReceivedAt),
            lastReceivedAt = g.Max(m => m.ReceivedAt)
        })
        .OrderByDescending(s => s.count)
        .ToListAsync();

    return Results.Ok(new
    {
        totalMessages,
        statsByType
    });
});

// Consulta un mensaje por ID.
// Ejemplo:
// GET /messages/1
app.MapGet("/messages/{id:int}", async (int id, AppDbContext db) =>
{
    var message = await db.Messages.FindAsync(id);

    return message is null
        ? Results.NotFound(new { error = "Mensaje no encontrado" })
        : Results.Ok(message);
});

app.Run();

public record PublishMessageRequest(string Type, string Content);