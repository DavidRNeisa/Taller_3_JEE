# Message Consumer (minimal)

Proyecto .NET minimal que consume mensajes desde una cola (simulada) y los guarda en SQLite via EF Core.

Run:

1. Instalar .NET 7 SDK.
2. Abrir carpeta `message-consumer` y ejecutar:

```bash
dotnet restore
dotnet run
```

La API escucha en `http://localhost:5000` (por defecto). Endpoints:

- `GET /messages` — lista de mensajes
- `GET /messages?type=task` — filtra por tipo

Notas: reemplazar `InMemoryQueueClient` por cliente real (Azure Service Bus, RabbitMQ) si se desea.
