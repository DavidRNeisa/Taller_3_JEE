Proyecto Taller_3_JEE — Instrucciones rápidas

Backend (Spring Boot)
- Requisitos: Java 17+, Maven, PostgreSQL corriendo en localhost:5432, base `taller3_jee` con usuario `postgres`/`postgres` (ajustar en `backend/src/main/resources/application.properties`).
- Ejecutar desde `backend/`:

```bash
./mvnw spring-boot:run
```

Frontend (Angular)
- Requisitos: Node.js, npm
- Ejecutar (modo desarrollo, con proxy a backend):

```bash
cd frontend
npm install
npm start
```

Esto usa `proxy.conf.json` para reenviar `/api` a `http://localhost:8080`.

Construcción para producción
- Generar frontend y copiar la carpeta `dist` al backend para que Spring Boot sirva los archivos estáticos:

```bash
cd frontend
npm run build -- --configuration production
# Copiar contenido de dist/<app-name> a backend/src/main/resources/static
```

Notas
- CORS ya está configurado para permitir `http://localhost:4200` (archivo `backend/src/main/java/com/example/backend/config/CorsConfig.java`).
- Los endpoints expuestos por el backend están bajo `/api/*` y coinciden con los `environment.apiUrl` del frontend.
