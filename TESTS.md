## Enunciado de taller: Implementación de Pruebas

Con base en el Taller No. 2 de Jakarta, realizar las siguientes pruebas técnicas
    - Disponibilidad
    - Escalabilidad

Con base en el taller No. 2 de Registra las pruebas tenicas.

Realizar las pruebas de usabilidad de las presentacaiones realizadas en JEE y .NET.

Realizar las pruebas de aceptación.


## Idea propuesta de lo que debemos hacer:

### 1. **Pruebas técnicas de disponibilidad y escalabilidad**
   - **Disponibilidad**: 
     - Diseñar pruebas que simulen múltiples usuarios accediendo al sistema simultáneamente.
     - Utilizar herramientas como JMeter o Gatling para realizar pruebas de carga.
     - Medir el tiempo de respuesta y la tasa de errores bajo diferentes niveles de carga.
   - **Escalabilidad**:
     - Probar el sistema con diferentes configuraciones de hardware o instancias.
     - Evaluar cómo el sistema maneja un aumento progresivo en la carga.

### 1.1 **Ejecución de la prueba técnica realizada**
    - **Herramienta utilizada**: script de carga en `tests/load_test.sh`.
    - **Endpoint probado**: `http://localhost:8080/api/cursos`.
    - **Comando ejecutado**:
       - `bash tests/load_test.sh http://localhost:8080/api/cursos 200 30`
    - **Resultado de disponibilidad**:
       - El servicio respondió durante la ejecución de la prueba y el script finalizó con código `0`.
       - Esto indica que el backend permaneció disponible bajo una carga concurrente básica.
    - **Resultado de escalabilidad**:
       - La prueba permitió validar que el endpoint soporta un volumen inicial de solicitudes sin interrupciones visibles.
       - El script puede reutilizarse aumentando el número de solicitudes o ajustando el nivel de concurrencia para comparar el comportamiento bajo mayor presión.
    - **Observación técnica**:
       - El helper intenta usar `ab` primero y, si no está disponible, hace fallback a `wrk` o a `curl` paralelo; por eso la prueba es reproducible en distintos entornos.

### 2. **Pruebas de usabilidad**
    - **Objetivo**: evaluar si las interfaces web desarrolladas en JEE y .NET permiten completar las tareas principales de forma clara, rápida y sin errores.
    - **Alcance**: pantallas de inicio de sesión, navegación principal, consulta de contenido, entrega de tareas y acciones de administración disponibles en ambas soluciones.
    - **Perfiles de usuario**:
       - Estudiante: ingresa, navega por el contenido y realiza una entrega.
       - Docente: publica contenido y revisa entregas.
       - Administrador: valida accesos y acceso a funciones generales del sistema.
    - **Tareas a evaluar**:
       - Acceder a la aplicación e iniciar sesión correctamente.
       - Ubicar una funcionalidad desde el menú principal sin ayuda externa.
       - Consultar contenido académico o recursos publicados.
       - Enviar una tarea o evidencia desde la interfaz.
       - Cerrar sesión y volver al estado inicial.
    - **Método de evaluación**:
       - Aplicar pruebas moderadas con usuarios reales o compañeros que representen los perfiles anteriores.
       - Registrar observaciones durante la ejecución y aplicar una encuesta corta al finalizar.
       - Comparar la experiencia entre la interfaz JEE y la interfaz .NET usando las mismas tareas.
    - **Criterios de medición**:
       - Tiempo para completar cada tarea.
       - Cantidad de errores o intentos fallidos.
       - Número de pasos necesarios para llegar a la acción esperada.
       - Nivel de satisfacción percibida por el usuario.
       - Problemas de accesibilidad o lectura detectados.
    - **Preguntas sugeridas para la encuesta**:
       - ¿La navegación fue clara y fácil de entender?
       - ¿Encontraste rápidamente la opción que necesitabas?
       - ¿El diseño visual te ayudó o dificultó completar la tarea?
       - ¿Hubo textos, botones o mensajes confusos?
       - ¿La interfaz funcionó bien en computador y en distintas resoluciones?
    - **Criterio de aprobación**:
       - La tarea se considera aceptada si el usuario la completa sin asistencia, en un tiempo razonable y sin errores críticos de navegación.

### 2.1 **Resultados de la prueba de usabilidad ejecutada**
    - **Entorno probado**:
       - Frontend Angular ejecutado en `http://localhost:4200/`.
       - Backend Spring Boot ejecutado en el puerto `8080` con PostgreSQL local levantado en `5432`.
    - **Resultado general**:
       - La interfaz principal carga correctamente y presenta un inicio simple con accesos directos a cursos y recomendaciones.
       - Con PostgreSQL activo, los flujos funcionales cargan datos reales y el recorrido principal puede completarse.
    - **Tareas verificadas**:
       - **Acceso a la portada**: cumplido. La pantalla inicial es clara y presenta el propósito de la plataforma.
       - **Ingreso al curso / ver contenido**: cumplido. La lista de cursos carga y permite entrar a la lección.
       - **Panel de recomendaciones**: cumplido. El panel muestra el siguiente tema sugerido y el estado general de avance.
       - **Vista de lección**: cumplido. La clase 1 carga su contenido, recursos y navegación interna.
       - **Búsqueda de login**: no evaluable, porque no se identifica un flujo de autenticación en la UI actual.
    - **Hallazgos de usabilidad**:
       - La navegación es visualmente simple, pero muy dependiente del estado del backend.
       - Los botones “Entrar al curso” y “Ver contenido” llevan al mismo destino, lo que genera redundancia en la experiencia.
       - No existe una barra de navegación persistente; el recorrido depende de enlaces puntuales de cada pantalla.
       - El contenido de la lección se presenta de forma extensa y legible, pero la página puede resultar larga para pantallas pequeñas.
       - El sistema mantiene una jerarquía visual clara entre portada, cursos, recomendaciones y contenido de clase.
    - **Conclusión de la ejecución**:
       - La experiencia de uso es aceptable en la capa visual inicial y en el recorrido funcional principal.
       - El backend y PostgreSQL permitieron completar la prueba de usabilidad con datos reales.
       - No se pudo validar una interfaz web .NET separada en el workspace; la solución .NET encontrada corresponde a un consumidor de mensajería por consola, no a una pantalla web.

### 3. **Pruebas de aceptación**
   - Validar que el sistema cumple con los requisitos funcionales especificados.
   - Crear casos de prueba basados en los escenarios descritos en el diseño arquitectónico.
   - Incluir validaciones para las funcionalidades principales como:
     - Inscripción de estudiantes.
     - Publicación de contenido.
     - Entrega y calificación de tareas.

### 4. **Documentación**
   - Crear un documento que explique cómo realizar cada tipo de prueba.
   - Incluir pasos detallados, herramientas utilizadas y métricas a evaluar.

### 5. **Automatización de pruebas**
    - Implementar pruebas automatizadas para las funcionalidades clave utilizando frameworks según la capa a validar.
    - **Implementación realizada en el backend**:
       - **JUnit 5** + **Mockito** para pruebas unitarias de servicios.
       - **MockMvc** y pruebas de controlador tipo contrato para validar la capa REST.
       - Flujos cubiertos: creación y edición de cursos, registro de entregas, cálculo de estado y nota, y generación de recomendaciones.
    - **Ejecución de las pruebas implementadas**:
       - Desde `backend/`: `JAVA_HOME=/home/davidrneisa/.jdk/jdk-17.0.16 ./mvnw clean test`
       - Para ejecutar solo la nueva suite: `JAVA_HOME=/home/davidrneisa/.jdk/jdk-17.0.16 ./mvnw clean -Dtest=CursoServiceTest,EntregaServiceTest,RecomendacionServiceTest,CursoControllerTest test`

### 6. **Entrega**
   - Asegurarse de que las pruebas sean reproducibles y estén documentadas.
   - Proveer scripts o configuraciones necesarias para ejecutar las pruebas.

---

## Informe tecnico consolidado (Angular + Spring Boot + PostgreSQL)

Fecha de ejecucion de evidencia: 2026-05-25

### 0. Alcance, entorno y arquitectura evaluada

- Frontend: Angular 18 (`frontend/`)
- Backend: Spring Boot (`backend/`)
- Base de datos: PostgreSQL (`taller3_jee`, puerto 5432)
- API REST + entidades JPA (tablas: `alumnos`, `cursos`, `clases`, `contenidos`, `tareas`, `entregas`, `calificaciones`, `recomendaciones`, `inscripciones`)

#### Endpoints evaluados en esta bateria

- `GET /api/cursos`
- `GET /api/cursos/{id}`
- `GET /api/clases/curso/{cursoId}`
- `GET /api/contenidos/clase/{claseId}`
- `GET /api/tareas/clase/{claseId}`
- `POST /api/entregas`
- `GET /api/calificaciones/{alumnoId}`
- `GET /api/recomendaciones/{alumnoId}`

Nota: en algunos enunciados aparece `GET /api/cursos/{id}/clases`, pero en este backend el endpoint implementado es `GET /api/clases/curso/{cursoId}`.

---

## 1) Pruebas de disponibilidad

### 1.1 Objetivo

Verificar que la plataforma LMS mantiene continuidad operativa, baja tasa de error y tiempos de respuesta aceptables ante multiples solicitudes concurrentes sobre endpoints criticos.

### 1.2 Alcance

- Disponibilidad de API REST.
- Estabilidad de endpoints de consulta de cursos, clases, contenidos, tareas, calificaciones y recomendaciones.
- Comportamiento ante carga concurrente inicial.
- Deteccion de errores HTTP y respuestas degradadas.

### 1.3 Metodologia

1. Levantar backend + frontend + PostgreSQL.
2. Ejecutar pruebas de sonda HTTP endpoint por endpoint (status, latencia, tamano de respuesta).
3. Ejecutar carga concurrente con ApacheBench (script `tests/load_test.sh`).
4. Evaluar errores, timeouts, disponibilidad y consistencia de respuesta.
5. Registrar hallazgos tecnicos, umbrales y criterios PASS/FAIL.

### 1.4 Herramientas recomendadas

- `curl` (sonda y validacion rapida)
- ApacheBench (`ab`) (carga concurrente)
- `k6` (escenarios mas ricos por endpoint)
- Postman + Newman (regresion API)
- Cypress (validacion e2e desde UI)

### 1.5 Escenarios de disponibilidad

| ID | Escenario | Tipo | Objetivo |
|---|---|---|---|
| DISP-01 | Sonda de endpoints criticos | API | Confirmar HTTP 200 y latencia base |
| DISP-02 | Carga concurrente corta (`ab -n 200 -c 10`) | API | Verificar continuidad sin errores |
| DISP-03 | Sonda con payload incompleto (`POST /api/entregas` sin archivo) | API negativo | Validar manejo de error y resiliencia |
| DISP-04 | Smoke frontend build | UI/entrega | Confirmar disponibilidad del artefacto frontend |

### 1.6 Metricas, objetivos y criterios PASS/FAIL

| Metrica | Objetivo | PASS | FAIL |
|---|---|---|---|
| Disponibilidad API | >= 99.0% en ventana de prueba | Errores <= 1% | Errores > 1% |
| HTTP 5xx | <= 1% | 0-1% | > 1% |
| p95 latencia lectura | < 600 ms | < 600 ms | >= 600 ms |
| Timeouts | 0 en carga base | 0 | >= 1 |
| Recuperacion post error | < 30 s | sistema responde normal | degradacion persistente |

### 1.7 Evidencia ejecutada (real)

#### 1.7.1 Sonda `curl` por endpoint

| Endpoint | HTTP | Latencia (s) | Tamano respuesta (bytes) | Resultado |
|---|---:|---:|---:|---|
| `/api/cursos` | 200 | 0.002239 | 165 | OK |
| `/api/cursos/1` | 200 | 0.002021 | 163 | OK |
| `/api/clases/curso/1` | 200 | 0.002061 | 12040 | OK |
| `/api/contenidos/clase/1` | 200 | 0.001820 | 965 | OK |
| `/api/tareas/clase/1` | 200 | 0.002186 | 550 | OK |
| `/api/calificaciones/1` | 200 | 0.001517 | 2 | OK |
| `/api/recomendaciones/1` | 200 | 0.001634 | 2 | OK |

Comando base utilizado:

```bash
for u in \
   http://localhost:8080/api/cursos \
   http://localhost:8080/api/cursos/1 \
   http://localhost:8080/api/clases/curso/1 \
   http://localhost:8080/api/contenidos/clase/1 \
   http://localhost:8080/api/tareas/clase/1 \
   http://localhost:8080/api/calificaciones/1 \
   http://localhost:8080/api/recomendaciones/1; do
   code=$(curl -s -o /tmp/lms_resp.json -w "%{http_code}" "$u")
   time=$(curl -s -o /dev/null -w "%{time_total}" "$u")
   bytes=$(wc -c < /tmp/lms_resp.json)
   echo "$u|$code|$time|$bytes"
done
```

#### 1.7.2 Carga concurrente disponibilidad (`ab`)

Comando:

```bash
bash tests/load_test.sh http://localhost:8080/api/cursos 200 30
```

Resultado observado:

- Requests: 200
- Concurrency: 10
- Failed requests: 0
- Requests/sec: 4663.20
- Mean time/request: 2.144 ms
- Max request time: 8 ms

Interpretacion:

- El backend se mantuvo disponible bajo carga concurrente basica.
- No se observaron fallos HTTP ni timeouts en este escenario.

#### 1.7.3 Caso negativo de disponibilidad (error controlado)

Comando:

```bash
curl -X POST "http://localhost:8080/api/entregas?alumnoId=1&tareaId=1"
```

Respuesta observada:

- HTTP: 500
- Mensaje: `Current request is not a multipart request`

Interpretacion tecnica:

- El endpoint valida multipart, pero devuelve 500; para robustez productiva conviene normalizar a 400 (Bad Request) con contrato de error estable.

### 1.8 Logs a revisar

- Backend: stack traces de `MultipartException`, `HttpMessageNotReadableException`, errores 5xx.
- PostgreSQL: saturacion de conexiones, locks y consultas lentas.
- Frontend: consola del navegador (errores `ERR_CONNECTION_REFUSED`, fallos de proxy, 5xx API).

### 1.9 SQL de validacion (disponibilidad + consistencia)

Archivo entregado: `tests/sql/acceptance_validation.sql`

Ejecucion:

```bash
PGPASSWORD=postgres psql -h localhost -U postgres -d taller3_jee -f tests/sql/acceptance_validation.sql
```

### 1.10 Riesgos encontrados (disponibilidad)

1. Manejo de error en `POST /api/entregas` retorna 500 para input invalido.
2. Dependencia fuerte frontend-backend: en UI se observaron eventos de `ERR_CONNECTION_REFUSED`.
3. Ausencia de monitoreo APM estandar (no hay dashboard de p95/p99 ni alertas declaradas en el repo).

### 1.11 Conclusion tecnica (disponibilidad)

Para carga base y concurrente inicial, el LMS cumple disponibilidad operativa. El principal punto de mejora es endurecer manejo de errores (4xx vs 5xx), telemetria y resiliencia frente a caidas de backend.

---

## 2) Pruebas de escalabilidad

### 2.1 Objetivo

Medir comportamiento del sistema ante crecimiento progresivo de concurrencia, identificando limites, degradacion y cuellos de botella en API y persistencia.

### 2.2 Escenarios de crecimiento ejecutados

`ab -n 1000` sobre `GET /api/cursos` con concurrencia: 10, 25, 50, 100.

| Escenario | Failed req | Req/s | Mean time/request |
|---|---:|---:|---:|
| c=10 | 0 | 15043.02 | 0.665 ms |
| c=25 | 0 | 16831.62 | 1.485 ms |
| c=50 | 0 | 16309.22 | 3.066 ms |
| c=100 | 0 | 15256.00 | 6.555 ms |

### 2.3 Analisis de degradacion

- No hubo errores bajo las cargas evaluadas.
- A mayor concurrencia, la latencia media crece de forma esperada (sublineal en este rango).
- Throughput pico en c=25 y leve descenso posterior (patron tipico de saturacion progresiva de recursos compartidos).

### 2.4 Criterios de escalabilidad (propuestos)

Se considera que escala correctamente si:

1. `failed_requests <= 1%`
2. crecimiento de latencia es gradual (sin saltos abruptos > 2x entre etapas consecutivas comparables)
3. throughput no colapsa mas de 30% al doblar concurrencia
4. sin timeouts ni errores de conexion DB

Se considera degradacion critica si:

1. `failed_requests > 5%`
2. p95 > 2000 ms de forma sostenida
3. saturacion de pool de conexiones o errores `too many clients`
4. backlog sostenido en peticiones y caida de throughput > 50%

### 2.5 Pruebas recomendadas con k6 (script entregado)

Archivo: `tests/performance/k6/lms_api_load.js`

Ejecucion:

```bash
k6 run tests/performance/k6/lms_api_load.js \
   -e BASE_URL=http://localhost:8080 \
   -e ALUMNO_ID=1 -e CURSO_ID=1 -e CLASE_ID=1
```

Este script incluye:

- rampa de VUs (50 -> 200)
- thresholds para error rate y latencia
- trafico sobre todos los endpoints criticos

### 2.6 Plantilla JMeter (configuracion base)

Escenario recomendado:

1. Thread Group 1: 20 usuarios, ramp-up 20s, 3 min
2. Thread Group 2: 80 usuarios, ramp-up 60s, 5 min
3. Thread Group 3: 150 usuarios, ramp-up 120s, 8 min

Samplers HTTP:

- GET `/api/cursos`
- GET `/api/cursos/${cursoId}`
- GET `/api/clases/curso/${cursoId}`
- GET `/api/contenidos/clase/${claseId}`
- GET `/api/tareas/clase/${claseId}`
- GET `/api/calificaciones/${alumnoId}`
- GET `/api/recomendaciones/${alumnoId}`

Listeners recomendados:

- Summary Report
- Aggregate Report
- Response Time Percentiles

### 2.7 Monitoreo backend y PostgreSQL

Metricas a capturar durante carga:

- CPU proceso Java
- heap usage y GC pauses
- hilos activos (Tomcat)
- conexiones activas/espera en pool (Hikari)
- conexiones activas PostgreSQL
- consultas lentas (EXPLAIN ANALYZE)

Consultas SQL utiles (PostgreSQL):

```sql
SELECT datname, numbackends, xact_commit, xact_rollback
FROM pg_stat_database
WHERE datname = 'taller3_jee';

SELECT state, count(*)
FROM pg_stat_activity
WHERE datname = 'taller3_jee'
GROUP BY state;
```

### 2.8 Riesgos de produccion (escalabilidad)

1. Posible saturacion progresiva en endpoints de lectura sin cache.
2. Riesgo de latencias altas con payloads extensos (`/api/clases/curso/{id}`).
3. Falta de validacion de capacidad maxima para entregas (subida de archivos) en alta concurrencia.

### 2.9 Recomendaciones de optimizacion

1. Definir `p95/p99 SLO` por endpoint y alertas activas.
2. Ajustar pool de conexiones y timeouts de datasource.
3. Incorporar paginacion en endpoints potencialmente grandes.
4. Estandarizar manejo de excepciones para no convertir errores de cliente en 500.
5. Ejecutar pruebas con datos volumetricos mas cercanos a produccion.

---

## 3) Pruebas de aceptacion

### 3.1 Objetivo

Validar funcionalmente que los flujos criticos del LMS cumplan requisitos de negocio y consistencia entre frontend, backend y base de datos.

### 3.2 Cobertura minima incluida

- Cursos
- Lecciones
- Contenido
- Tareas
- Entregas
- Calificaciones
- Recomendaciones
- Navegacion

### 3.3 Evidencia automatizada ejecutada

#### Backend (JUnit)

Comando ejecutado:

```bash
cd backend
JAVA_HOME=/home/davidrneisa/.jdk/jdk-17.0.16 ./mvnw -Dtest=CursoServiceTest,EntregaServiceTest,RecomendacionServiceTest,CursoControllerTest test
```

Resultado:

- Tests run: 15
- Failures: 0
- Errors: 0
- Build: SUCCESS

#### Frontend smoke

Comando ejecutado:

```bash
bash tests/frontend_smoke.sh
```

Resultado:

- Build Angular exitoso.
- Artefacto generado en `frontend/dist/frontend`.
- `index.html` y bundles JS detectados.

### 3.4 Matriz de casos de aceptacion (AC)

| ID | Modulo | Objetivo | Precondiciones | Pasos clave | Resultado esperado | Evidencia esperada | SQL de validacion | Endpoint |
|---|---|---|---|---|---|---|---|---|
| AC-01 | Cursos | Listar cursos | Backend+DB arriba | Abrir cursos / consumir API | Lista no vacia o respuesta valida | captura UI + JSON | `SELECT count(*) FROM cursos;` | `GET /api/cursos` |
| AC-02 | Cursos | Ver detalle curso | Curso id=1 existe | Abrir curso 1 | Datos de curso visibles | URL + payload | `SELECT * FROM cursos WHERE id=1;` | `GET /api/cursos/1` |
| AC-03 | Lecciones | Consultar clases por curso | Curso con clases | Navegar a curso | 1..n clases visibles | lista de lecciones | `SELECT count(*) FROM clases WHERE curso_id=1;` | `GET /api/clases/curso/1` |
| AC-04 | Contenido | Ver contenido por clase | Clase id=1 existe | Entrar a clase | Recursos/texto visibles | evidencia HTML | `SELECT count(*) FROM contenidos WHERE clase_id=1;` | `GET /api/contenidos/clase/1` |
| AC-05 | Tareas | Ver tareas por clase | Clase con tarea | Abrir panel tareas | Tarea listada | captura tareas | `SELECT count(*) FROM tareas WHERE clase_id=1;` | `GET /api/tareas/clase/1` |
| AC-06 | Entregas + | Crear entrega valida | alumno/tarea validos + archivo | POST multipart entrega | Registro entrega creado | respuesta 2xx + id | `SELECT * FROM entregas WHERE alumno_id=1 AND tarea_id=1 ORDER BY id DESC LIMIT 1;` | `POST /api/entregas` |
| AC-07 | Entregas - | Rechazar entrega sin archivo | alumno/tarea validos | POST sin multipart | Error validado | body error + status | N/A | `POST /api/entregas` |
| AC-08 | Calificaciones | Consultar notas alumno | Alumno existe | Abrir calificaciones | respuesta coherente | JSON notas | `SELECT * FROM calificaciones WHERE alumno_id=1;` | `GET /api/calificaciones/1` |
| AC-09 | Recomendaciones | Consultar recomendaciones | Alumno existe | Abrir recomendacion | lista/objeto valido | JSON recomendacion | `SELECT * FROM recomendaciones WHERE alumno_id=1;` | `GET /api/recomendaciones/1` |
| AC-10 | Navegacion | Flujo home->cursos->curso | Frontend arriba | Click en links principales | Navegacion sin bloqueo | video/capturas | N/A | UI + REST |
| AC-11 | Integridad | Sin huerfanos de entrega | Datos previos cargados | Ejecutar SQL integridad | 0 registros invalidos | salida SQL | query en `acceptance_validation.sql` | DB |
| AC-12 | Inscripcion | Evitar duplicados | Alumno/curso existentes | intentar doble alta | no duplica | log/regla activa | `GROUP BY alumno_id, curso_id HAVING count(*)>1` | modulo inscripciones |

Leyenda prioridad:

- Alta: AC-01..AC-10
- Media: AC-11..AC-12

### 3.5 Casos positivos, negativos y limite

- Positivos: AC-01, AC-02, AC-03, AC-04, AC-05, AC-08, AC-09, AC-10
- Negativos: AC-07 (sin multipart), error de backend no disponible (UI)
- Limite: cargas concurrentes altas en endpoints de lectura (fase escalabilidad)

### 3.6 Automatizacion propuesta

#### Cypress

Archivo existente y ampliado:

- `tests/acceptance/cypress/sample_spec.cy.js`
- `tests/acceptance/cypress/lms_acceptance.cy.js`

Ejecucion:

```bash
cd frontend
npx cypress run --spec "../tests/acceptance/cypress/lms_acceptance.cy.js"
```

#### Selenium

Archivo: `tests/acceptance/selenium/lms_acceptance_test.py`

Ejecucion:

```bash
python3 -m pip install selenium
python3 tests/acceptance/selenium/lms_acceptance_test.py
```

### 3.7 Convenciones de organizacion de pruebas

Estructura recomendada y aplicada:

- `tests/performance/k6/` -> scripts de carga y escalabilidad
- `tests/postman/` -> colecciones API
- `tests/sql/` -> validaciones de integridad y volumen
- `tests/acceptance/cypress/` -> e2e frontend
- `tests/acceptance/selenium/` -> regresion UI cross-tool

Convencion de nombres:

- `AC-xx_<modulo>_<flujo>.md` para casos manuales
- `lms_<tipo>_<objetivo>.<ext>` para scripts automatizados

---

## 4) Documentacion final para informe academico

### 4.1 Resumen ejecutivo

La plataforma LMS evaluada (Angular + Spring Boot + PostgreSQL) presenta comportamiento estable en disponibilidad y escalabilidad inicial, con tiempos de respuesta bajos y ausencia de errores bajo carga concurrente de lectura. Se identifican oportunidades de mejora en resiliencia de errores (especialmente en `POST /api/entregas`) y observabilidad operacional para ambientes de produccion.

### 4.2 Resultado global por fase

| Fase | Estado | Evidencia principal |
|---|---|---|
| Disponibilidad | APROBADA (escenario base) | `curl` + `ab` sin errores |
| Escalabilidad | APROBADA (rango evaluado) | `ab` c=10..100, 0 fallos |
| Aceptacion funcional | APROBADA PARCIAL | JUnit 15/15 + smoke frontend OK |

Nota sobre aceptacion parcial: la suite Cypress/Selenium ampliada queda entregada y lista para ejecucion, pero no fue ejecutada en este ciclo porque no hay dependencia Cypress instalada en `frontend/package.json` ni binario Selenium/WebDriver preconfigurado en el entorno.

### 4.3 Hallazgos tecnicos relevantes

1. API critica responde con latencias de ~1-3 ms en entorno local de prueba.
2. Escalado de concurrencia hasta 100 conexiones sin errores HTTP.
3. Manejo de multipart en entregas debe devolver error de cliente controlado (4xx) en lugar de 500.
4. Hubo eventos de frontend con `ERR_CONNECTION_REFUSED` en sesiones de navegador cuando backend no estaba disponible.

### 4.4 Recomendaciones finales

1. Implementar `@ControllerAdvice` para mapear validaciones y errores de entrada a respuestas 4xx uniformes.
2. Definir SLOs oficiales por endpoint (`p95`, `p99`, error rate) y monitoreo continuo.
3. Asegurar pruebas de carga con escenarios mixtos (lectura + escritura + subida de archivos).
4. Automatizar pipeline de pruebas con stages: unitarias -> API contract -> carga -> e2e.
5. Versionar evidencia por corrida (`reports/yyyy-mm-dd/`) con JSON de resultados y capturas.

---

## 5) Anexos de ejecucion rapida

### 5.1 Disponibilidad (curl)

```bash
curl -i http://localhost:8080/api/cursos
curl -i http://localhost:8080/api/cursos/1
curl -i http://localhost:8080/api/clases/curso/1
curl -i http://localhost:8080/api/contenidos/clase/1
curl -i http://localhost:8080/api/tareas/clase/1
curl -i http://localhost:8080/api/calificaciones/1
curl -i http://localhost:8080/api/recomendaciones/1
```

### 5.2 Carga rapida (ab)

```bash
bash tests/load_test.sh http://localhost:8080/api/cursos 200 30
ab -n 1000 -c 50 http://localhost:8080/api/cursos
```

### 5.3 k6

```bash
k6 run tests/performance/k6/lms_api_load.js -e BASE_URL=http://localhost:8080
```

### 5.4 Postman/Newman

Coleccion: `tests/postman/LMS_Availability.postman_collection.json`

```bash
newman run tests/postman/LMS_Availability.postman_collection.json
```

### 5.5 SQL validacion

```bash
PGPASSWORD=postgres psql -h localhost -U postgres -d taller3_jee -f tests/sql/acceptance_validation.sql
```

