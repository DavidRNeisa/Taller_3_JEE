# Informe de Pruebas del Taller

## 1. Evaluación heurística exploratoria de usabilidad

Nota: esta sección documenta una evaluación heurística e interna del recorrido principal. No corresponde a pruebas con usuarios reales.

### 1.1 Objetivo

Evaluar de forma heurística el recorrido principal de la plataforma para identificar fricciones de navegación, claridad visual y consistencia de interacción.

### 1.2 Contexto del sistema

- Nombre: Plataforma Académica LMS
- Tipo: aplicación web
- Stack: Angular + Spring Boot + PostgreSQL
- Alcance explorado: portada, cursos, lección, recomendaciones y navegación básica

### 1.3 Hallazgos resumidos

- Redundancia entre acciones de entrada a curso y ver contenido.
- Ausencia de una navegación global persistente.
- Lecciones extensas que requieren bastante desplazamiento vertical.

### 1.4 Problemas observados

| Problema | Severidad | Evidencia | Impacto | Recomendación |
|---|---|---|---|---|
| Redundancia en acciones de navegación | Baja | Dos botones llevan al mismo destino | Confusión menor | Unificar o diferenciar la acción |
| Falta de navegación persistente | Media | Navegación puntual entre vistas | Menor eficiencia en recorridos largos | Agregar barra global fija |
| Contenido largo sin anclas | Baja | Lecciones con mucho desplazamiento | Fatiga visual en pantallas pequeñas | Añadir anclas o secciones colapsables |

### 1.5 Conclusión de usabilidad

La interfaz resulta operable en el flujo principal, pero presenta oportunidades claras de mejora en navegación y densidad de contenido. Esta evaluación no sustituye pruebas con usuarios reales.

### 1.6 Participantes y alcance

- Evaluadores: 1 (tester/desarrollador del proyecto)
- Número total de usuarios reales: 0
- Alcance: validación interna y heurística; las pruebas con usuarios reales quedan pendientes para una medición cuantitativa formal.

### 1.7 Metodología aplicada

- Tipo: evaluación heurística exploratoria, interna, no moderada con usuarios externos.
- Protocolo: el evaluador recorrió el flujo principal, anotando tiempos aproximados, errores observados, número de clics y comentarios contextuales.
- Limitaciones: los datos provienen de una sola ejecución interna y no tienen significación estadística.

### 1.8 Tareas evaluadas

- T1: Acceder a la portada y comprobar acciones principales.
- T2: Abrir la lista de cursos y verificar la carga.
- T3: Abrir la lección 1 y comprobar contenido y recursos.
- T4: Revisar el panel de recomendaciones.
- T5: Navegar de regreso al curso o portada.

### 1.9 Métricas y formato de recogida

| Tester | Tarea | Éxito (S/N) | Tiempo (s) | Errores | Nº clics (aprox.) | Abandono (S/N) | Satisfacción (1-5) | Comentarios |
|---|---|---:|---:|---:|---:|---:|---:|---|
| Evaluador-1 | T1 | S | (no medido) | 0 | 2 | N | 4 | Portada clara |
| Evaluador-1 | T2 | S | (no medido) | 0 | 3 | N | 3 | Entrar y ver contenido llevan al mismo destino |
| Evaluador-1 | T3 | S | (no medido) | 0 | 4 | N | 4 | Contenido cargado correctamente |
| Evaluador-1 | T4 | S | (no medido) | 0 | 2 | N | 4 | Recomendaciones visibles |
| Evaluador-1 | T5 | S | (no medido) | 0 | 2 | N | 4 | Navegación válida |

### 1.10 Observaciones y problemas identificados

- Redundancia en acciones de navegación: durante T2, dos botones conducen al mismo destino, lo que genera confusión leve.
- Ausencia de navegación persistente: el flujo depende de enlaces puntuales entre módulos.
- Contenido extenso en la vista de lección: las páginas requieren desplazamiento considerable y pueden fatigar en pantallas pequeñas.

| Problema | Severidad | Evidencia | Ocurrencias | Impacto | Recomendación |
|---|---:|---|---:|---|---|
| Redundancia en acciones de navegación | Baja | T2: "Entrar" y "Ver contenido" llevan al mismo destino | 1/1 | Confusión menor | Unificar o renombrar la acción |
| Falta de navegación persistente | Media | Navegación por enlaces puntuales; ausencia de barra global | 1/1 | Menor eficiencia en recorridos largos | Añadir barra de navegación persistente |
| Contenido largo sin anclas | Baja | Lecciones extensas requieren demasiado desplazamiento | 1/1 | Fatiga en pantallas pequeñas | Añadir anclas o colapsables para secciones |

### 1.11 Resultados y análisis

- La evaluación heurística preliminar indica que el flujo principal funciona en un entorno controlado y no se encontraron errores bloqueantes durante la ejecución interna.
- Los hallazgos cuantitativos son limitados por la ausencia de participantes reales.
- Las observaciones deben priorizarse y validarse en pruebas con usuarios reales, idealmente con N >= 5 a 8 participantes en una fase exploratoria inicial.

### 1.12 Recomendaciones operativas

- Priorizar correcciones de navegación, unificando accesos y añadiendo barra persistente.
- Preparar una sesión de pruebas con usuarios reales y capturar tiempos por tarea, número de errores y satisfacción.
- Instrumentar futuras sesiones con cronometraje automático, grabación de sesión y formulario de satisfacción al finalizar.

### 1.13 Conclusión

- Resultado: usabilidad preliminar aceptable en revisión interna, con mejoras recomendadas.
- Próximo paso: organizar pruebas con usuarios reales y completar la tabla por participante para obtener datos cuantitativos.

## 1. Contexto y alcance

Sistema evaluado: plataforma LMS web desarrollada con Angular + Spring Boot + PostgreSQL.

Flujos funcionales cubiertos por este informe:

- Inscripción de estudiantes a cursos.
- Publicación y consulta de contenido por docentes.
- Entrega de tareas por estudiantes.
- Calificación de tareas por docentes.
- Navegación entre cursos, lecciones y tareas.
- Visualización de recomendaciones.

Entorno observado en el workspace:

- Frontend Angular con rutas funcionales para cursos, lecciones, tareas, calificaciones y recomendaciones.
- Backend Spring Boot con controladores REST para cursos, clases, contenido, tareas, entregas, calificaciones y recomendaciones.
- Persistencia en PostgreSQL con entidades JPA y tablas de negocio como `cursos`, `clases`, `contenidos`, `tareas`, `entregas`, `calificaciones`, `inscripciones` y `recomendaciones`.

## 2. Hallazgos de referencia para la estrategia de prueba

Antes de definir la batería de aceptación, se identificaron estos puntos relevantes del sistema real:

- El frontend expone rutas para navegación académica: `/courses`, `/courses/:id`, `/courses/:courseId/lessons/:lessonId`, `/courses/:courseId/assignments`, `/courses/:courseId/assignments/:assignmentId/submit`, `/courses/:courseId/grades` y `/recommendations`.
- El backend expone endpoints REST como `GET /api/cursos`, `GET /api/cursos/{id}`, `GET /api/cursos/{id}/clases`, `GET /api/contenidos/clase/{claseId}`, `GET /api/tareas/clase/{claseId}`, `GET /api/tareas/{id}`, `POST /api/entregas`, `GET /api/calificaciones/{alumnoId}`, `GET /api/recomendaciones/{alumnoId}` y `POST /api/recomendaciones/{alumnoId}`.
- La persistencia está modelada con JPA en PostgreSQL. Por ejemplo, `inscripciones` relaciona `alumno_id` con `curso_id`, `entregas` referencia `alumno_id` y `tarea_id`, y `calificaciones` referencia `entrega_id`, `alumno_id` y `clase_id`.
- La seguridad actual permite acceso a `/api/**` y a los recursos públicos; por tanto, las restricciones de acceso deben validarse como requisito de negocio y, si no existen aún en implementación, registrarse como desviación.
- Los archivos de guardas e interceptor del frontend aparecen vacíos, por lo que el control de acceso y manejo centralizado de errores no está instrumentado en Angular al nivel esperado para un LMS maduro.

## 3. MATRIZ DE CASOS DE PRUEBA

### 3.1 Criterio de priorización

- Prioridad Alta: afecta el flujo principal de aprendizaje o la consistencia de datos.
- Prioridad Media: impacta navegación, legibilidad o recuperación ante fallos.
- Prioridad Baja: mejora experiencia, casos límite o consistencia secundaria.

| ID | Nombre del caso | Objetivo | Precondiciones | Pasos | Resultado esperado | Prioridad | Tipo de prueba | Evidencia esperada |
|---|---|---|---|---|---|---|---|---|
| AC-01 | Inscripción persistida en curso | Validar que un estudiante quede asociado a un curso | Existe alumno y curso en BD; entorno con PostgreSQL activo | Consultar matrícula inicial; ejecutar inscripción por flujo disponible o seed controlado; refrescar vista de curso | Se crea registro en `inscripciones`; el curso refleja estado inscrito | Alta | Funcional, persistencia | Captura de UI, SQL en `inscripciones`, log backend sin error |
| AC-02 | Reinscripción duplicada rechazada | Evitar duplicidad de matrícula | Existe inscripción previa para alumno-curso | Repetir intento de inscripción | El sistema impide duplicado o devuelve validación explícita | Alta | Negativa, integridad | Captura de mensaje, query que demuestre un solo registro |
| AC-03 | Carga de listado de cursos | Verificar consulta y renderizado del catálogo | Backend disponible con cursos semilla | Abrir `/courses` | Se listan cursos sin errores y con estado visual correcto | Alta | Funcional, UI/API | Captura de pantalla, red 200 en `GET /api/cursos` |
| AC-04 | Acceso a detalle de curso | Validar navegación y carga de lecciones | Existe curso con clases | Abrir detalle del curso desde listado | Se muestran datos del curso y sus lecciones asociadas | Alta | Funcional, navegación | Captura de detalle, `GET /api/cursos/{id}` y `GET /api/cursos/{id}/clases` |
| AC-05 | Lección válida con contenido | Verificar consulta de contenido por clase | Existe clase con contenidos | Abrir lección válida | Se renderiza la lección y sus recursos | Alta | Funcional, UI/API | Captura de recurso, `GET /api/contenidos/clase/{claseId}` |
| AC-06 | Lección inexistente | Validar error ante ID inválido | No existe lección con el ID solicitado | Navegar a una URL con `lessonId` inexistente | Se muestra mensaje de error y no hay bloqueo de la interfaz | Media | Negativa, robustez | Captura del error, log frontend/backend |
| AC-07 | Listado de tareas por curso | Validar organización y estado de tareas | Existe curso con tareas semilla | Abrir `/courses/:courseId/assignments` | Se muestran tareas separadas por estado y ordenadas por fecha límite | Alta | Funcional, UI/API | Captura del panel de tareas, `GET /api/tareas/clase/{claseId}` |
| AC-08 | Filtro de tareas por lección | Verificar el contexto por lección | Existe `lessonId` con tareas | Abrir listado con query param `lessonId` | Solo se muestran tareas de la lección seleccionada | Media | Funcional, navegación | Captura con filtro aplicado |
| AC-09 | Formulario de entrega válido | Registrar entrega de archivo permitido | Existe tarea abierta; archivo válido y alumno definido | Abrir formulario, seleccionar archivo válido y enviar | Se crea entrega en BD y UI muestra éxito | Alta | Funcional, persistencia | Captura de éxito, `POST /api/entregas`, SQL en `entregas` |
| AC-10 | Archivo inválido en entrega | Bloquear formato no permitido | Existe tarea abierta | Adjuntar archivo con extensión no permitida | Se muestra validación y no se habilita el envío | Alta | Negativa, validación de formulario | Captura del mensaje de error frontend |
| AC-11 | Archivo excede tamaño | Bloquear archivo mayor a 10 MB | Existe tarea abierta | Adjuntar archivo > 10 MB | Se rechaza archivo y se informa el límite | Alta | Límite, validación de formulario | Captura del error, ausencia de request al backend |
| AC-12 | Consulta de calificaciones | Verificar visualización de notas | Existen calificaciones para alumno-curso | Abrir `/courses/:courseId/grades` | Se listan calificaciones con promedio y estados correctos | Alta | Funcional, UI/API | Captura de tabla, `GET /api/calificaciones/{alumnoId}` |
| AC-13 | Calificación pendiente | Mostrar entregas sin calificar | Existe entrega no calificada | Consultar calificaciones | La entrega figura como pendiente de revisión | Media | Funcional | Captura de estado, query SQL en `calificaciones` |
| AC-14 | Recomendaciones con progreso | Validar panel de recomendaciones | Existen cursos inscritos y lecciones | Abrir `/recommendations` | Se genera panel con recomendaciones y métricas coherentes | Alta | Funcional, UI/API | Captura del panel, `GET/POST /api/recomendaciones/{alumnoId}` |
| AC-15 | Recomendaciones sin cursos inscritos | Validar estado vacío | El alumno no tiene cursos inscritos | Abrir recomendaciones | Se muestra estado informativo sin romper la vista | Media | Negativa, UI | Captura del estado vacío |
| AC-16 | Manejo de fallo backend en cursos | Validar resiliencia ante 500/timeout | Backend simulado caído o endpoint indisponible | Abrir catálogo | La UI muestra estado de carga/error sin colgarse | Alta | Negativa, resiliencia | Captura de error, logs del navegador |
| AC-17 | Restricción de acceso docente | Confirmar separación de roles | Usuario con rol estudiante | Intentar acceder a funcionalidad docente | El sistema debe bloquear el acceso o evidenciar falta de control de rol | Alta | Seguridad funcional | Captura de bloqueo o registro de desviación |
| AC-18 | Consistencia frontend-backend | Verificar que lo mostrado coincide con la API | Datos semilla cargados | Comparar respuesta JSON con la vista renderizada | Los campos y cantidades coinciden sin divergencias | Alta | Integración, consistencia | Captura UI, payload de red, SQL |
| AC-19 | Persistencia de contenido | Validar que clase y contenido sobreviven a recarga | Contenido creado en BD | Recargar lección y volver a consultar | El contenido persiste y se recupera igual | Alta | Persistencia | Captura antes/después, SQL en `contenidos` |
| AC-20 | Navegación entre lecciones | Verificar avance y retroceso | Curso con varias lecciones | Usar siguiente/anterior en lección | La ruta cambia correctamente y el estado de lección también | Media | Navegación | Captura de rutas y botón activo |

## 4. PRUEBAS DE ACEPTACIÓN DETALLADAS

### 4.1 Inscripción de estudiantes a cursos

#### Caso AC-01: Inscripción persistida en curso

- Objetivo: confirmar que el alumno queda inscrito y el registro queda almacenado en PostgreSQL.
- Precondiciones: existe un alumno en `alumnos`, un curso en `cursos` y el entorno usa la misma base de datos para frontend y backend.
- Pasos:
  1. Consultar el estado inicial en `inscripciones` para el par alumno-curso.
  2. Ejecutar el flujo de inscripción disponible en el entorno o un seed controlado de matrícula.
  3. Refrescar la vista de cursos y validar el estado visual.
  4. Consultar nuevamente la tabla `inscripciones`.
- Resultado esperado:
  - Se crea una fila nueva con `fechaInscripcion`, `alumno_id` y `curso_id`.
  - La UI refleja estado de inscripción sin inconsistencias.
  - El backend no registra excepción.
- Validaciones backend + frontend + BD:
  - Backend: respuesta HTTP 200/201 o validación funcional equivalente.
  - Frontend: etiqueta visual de inscrito o disponibilidad del curso acorde al estado.
  - BD: una sola fila en `inscripciones` por alumno-curso.
- Evidencia:
  - Captura de la vista de curso.
  - Log del backend sin stacktrace.
  - SQL:

```sql
SELECT id, fecha_inscripcion, alumno_id, curso_id
FROM inscripciones
WHERE alumno_id = 1 AND curso_id = 1;
```

  - Endpoint relacionado: `GET /api/cursos`, `GET /api/cursos/{id}`.

#### Caso AC-02: Reinscripción duplicada rechazada

- Objetivo: evitar registros duplicados para el mismo alumno y curso.
- Precondiciones: existe inscripción previa.
- Pasos:
  1. Repetir el intento de matrícula.
  2. Observar mensaje de validación o resultado del flujo.
  3. Revisar BD.
- Resultado esperado:
  - El sistema rechaza la operación o no crea una segunda fila.
  - Se muestra mensaje claro al usuario.
- Evidencia:
  - Captura del mensaje.
  - SQL de conteo:

```sql
SELECT COUNT(*) AS total
FROM inscripciones
WHERE alumno_id = 1 AND curso_id = 1;
```

#### Caso AC-03: Catálogo de cursos

- Objetivo: verificar que el listado consume `GET /api/cursos` y se pinta correctamente.
- Precondiciones: backend activo y con cursos semilla.
- Pasos:
  1. Abrir `/courses`.
  2. Esperar el estado de carga.
  3. Validar tarjetas, títulos y progreso.
- Resultado esperado:
  - La vista deja el estado de carga.
  - Se muestran cursos con datos consistentes.
  - El navegador recibe una respuesta 200 del endpoint.
- Evidencia:
  - Captura de la grilla de cursos.
  - Network tab con `GET /api/cursos`.

#### Caso AC-04: Detalle de curso y lecciones

- Objetivo: comprobar navegación a detalle y carga de lecciones.
- Precondiciones: curso con clases asociadas.
- Pasos:
  1. Desde el listado, abrir un curso.
  2. Verificar título, descripción y progreso.
  3. Validar listado de lecciones.
- Resultado esperado:
  - Se muestran los datos del curso.
  - Las lecciones aparecen en el orden esperado.
- Evidencia:
  - Captura del detalle.
  - Endpoint: `GET /api/cursos/{id}` y `GET /api/cursos/{id}/clases`.
  - SQL:

```sql
SELECT c.id, c.titulo, c.descripcion, c.total_clases, cl.id AS clase_id, cl.numero, cl.titulo AS clase_titulo
FROM cursos c
LEFT JOIN clases cl ON cl.curso_id = c.id
WHERE c.id = 1
ORDER BY cl.numero;
```

#### Caso AC-05: Lección con contenido y recursos

- Objetivo: validar que la vista de lección consulta contenido por clase.
- Precondiciones: clase con recursos y contenido asociado.
- Pasos:
  1. Abrir `/courses/:courseId/lessons/:lessonId`.
  2. Confirmar el estado de carga.
  3. Verificar recursos embebidos y navegación entre lecciones.
- Resultado esperado:
  - Se renderiza la lección válida.
  - Los recursos usan el tipo correcto y no se rompen en el frontend.
- Evidencia:
  - Captura del viewer.
  - Network tab con `GET /api/contenidos/clase/{claseId}`.
  - SQL:

```sql
SELECT id, clase_id, titulo, tipo, url, orden
FROM contenidos
WHERE clase_id = 1
ORDER BY orden;
```

#### Caso AC-06: Lección inexistente

- Objetivo: validar manejo de error ante un identificador inválido.
- Precondiciones: no existe la lección solicitada.
- Pasos:
  1. Navegar a una ruta con `lessonId` inexistente.
  2. Esperar la respuesta del backend y el renderizado del error.
- Resultado esperado:
  - Se muestra un mensaje de error funcional.
  - No queda la pantalla bloqueada en `loading`.
- Evidencia:
  - Captura del mensaje de error.
  - Log del frontend con la traza controlada.

### 4.2 Publicación de contenido por docentes

#### Caso AC-07: Listado de tareas por curso

- Objetivo: validar que las tareas se listan por curso y estado de entrega.
- Precondiciones: curso con tareas semilla.
- Pasos:
  1. Abrir `/courses/:courseId/assignments`.
  2. Revisar bloques de pendientes, entregadas, tardías y no entregadas.
  3. Confirmar que las fechas se formatean correctamente.
- Resultado esperado:
  - Las tareas aparecen agrupadas y ordenadas por fecha límite.
  - El estado visual coincide con el backend.
- Evidencia:
  - Captura del listado.
  - Endpoint: `GET /api/tareas/clase/{claseId}`.

#### Caso AC-08: Filtro por lección en tareas

- Objetivo: verificar la navegación contextual desde una lección específica.
- Precondiciones: la URL admite `lessonId` como query param.
- Pasos:
  1. Abrir el listado con `lessonId` en query string.
  2. Verificar que solo se muestran tareas de esa lección.
- Resultado esperado:
  - El filtro se aplica sin perder consistencia de datos.
- Evidencia:
  - Captura con filtro activo.
  - Consulta SQL filtrada por `clase_id` relacionada.

#### Caso AC-09: Persistencia de contenido publicado

- Objetivo: validar que el contenido no desaparece tras recarga o nueva consulta.
- Precondiciones: contenido ya creado.
- Pasos:
  1. Consultar la lección.
  2. Recargar la página.
  3. Reconsultar el endpoint de contenido.
- Resultado esperado:
  - El contenido persiste en `contenidos`.
  - La UI mantiene los mismos datos.
- Evidencia:
  - Captura antes/después.
  - SQL en `contenidos`.

### 4.3 Entrega de tareas por estudiantes

#### Caso AC-10: Entrega exitosa con archivo válido

- Objetivo: registrar una entrega con formato permitido.
- Precondiciones: existe tarea y archivo permitido (`pdf`, `doc`, `docx`, `zip`, `rar`, `txt`).
- Pasos:
  1. Abrir `/courses/:courseId/assignments/:assignmentId/submit`.
  2. Seleccionar archivo válido.
  3. Enviar el formulario.
  4. Verificar persistencia.
- Resultado esperado:
  - Se crea un registro en `entregas`.
  - La UI muestra confirmación de envío.
- Evidencia:
  - Captura del éxito.
  - Request `POST /api/entregas`.
  - SQL:

```sql
SELECT id, fecha_entrega, archivo_url, estado_entrega, alumno_id, tarea_id
FROM entregas
WHERE alumno_id = 1 AND tarea_id = 1
ORDER BY id DESC;
```

#### Caso AC-11: Formato de archivo no permitido

- Objetivo: bloquear archivos con extensión inválida.
- Precondiciones: tarea disponible.
- Pasos:
  1. Adjuntar un archivo con extensión no soportada.
  2. Intentar continuar.
- Resultado esperado:
  - El formulario muestra el mensaje de validación.
  - No se habilita el botón de envío.
  - No se invoca el backend.
- Evidencia:
  - Captura del mensaje `Formato no permitido`.
  - Ausencia de request en Network.

#### Caso AC-12: Archivo que excede 10 MB

- Objetivo: validar límite de tamaño del archivo.
- Precondiciones: tarea disponible.
- Pasos:
  1. Adjuntar archivo mayor a 10 MB.
  2. Observar el estado del formulario.
- Resultado esperado:
  - Se informa el límite máximo.
  - No se procesa la entrega.
- Evidencia:
  - Captura del error `El archivo supera el limite de 10 MB.`

#### Caso AC-13: Error de backend al registrar entrega

- Objetivo: validar el manejo de fallo en `POST /api/entregas`.
- Precondiciones: endpoint indisponible o respuesta 500 simulada.
- Pasos:
  1. Completar formulario válido.
  2. Forzar fallo del backend.
  3. Enviar entrega.
- Resultado esperado:
  - La UI muestra error amigable.
  - El estado `submitting` se libera.
- Evidencia:
  - Captura del mensaje `No se pudo registrar la entrega. Intenta nuevamente.`
  - Log del backend con la causa real.

### 4.4 Calificación de tareas por docentes

#### Caso AC-14: Visualización de calificaciones

- Objetivo: confirmar que el docente o alumno ve las calificaciones del curso.
- Precondiciones: existen notas asociadas al alumno.
- Pasos:
  1. Abrir `/courses/:courseId/grades`.
  2. Validar promedio, estados y fechas.
- Resultado esperado:
  - Las calificaciones se muestran ordenadas y con etiqueta correcta.
- Evidencia:
  - Captura de la tabla.
  - Endpoint `GET /api/calificaciones/{alumnoId}`.
  - SQL:

```sql
SELECT id, nota, fecha_calificacion, entrega_id, alumno_id, clase_id
FROM calificaciones
WHERE alumno_id = 1
ORDER BY fecha_calificacion DESC;
```

#### Caso AC-15: Entrega pendiente de revisión

- Objetivo: validar que una entrega sin nota se clasifique como pendiente.
- Precondiciones: entrega registrada sin calificación.
- Pasos:
  1. Consultar calificaciones del curso.
  2. Localizar el ítem pendiente.
- Resultado esperado:
  - Se presenta como `Pendiente de revision`.
- Evidencia:
  - Captura del estado.
  - SQL de verificación en `calificaciones`.

#### Caso AC-16: Persistencia de calificación

- Objetivo: comprobar que una nota queda almacenada y visible tras recarga.
- Precondiciones: existe una calificación nueva.
- Pasos:
  1. Registrar o simular la calificación.
  2. Recargar el frontend.
  3. Reconsultar la API.
- Resultado esperado:
  - La nota persiste en `calificaciones`.
  - La vista no diverge respecto de la API.

### 4.5 Navegación entre cursos y lecciones

#### Caso AC-17: Navegación anterior/siguiente en lección

- Objetivo: comprobar avance secuencial entre lecciones.
- Precondiciones: curso con al menos dos lecciones.
- Pasos:
  1. Abrir una lección intermedia.
  2. Pulsar anterior.
  3. Pulsar siguiente.
- Resultado esperado:
  - La ruta cambia correctamente.
  - El contenido mostrado corresponde a la lección navegada.
- Evidencia:
  - Captura de URLs y contenido.

#### Caso AC-18: Manejo de ID inválido en curso o detalle

- Objetivo: validar robustez ante rutas con identificadores inexistentes.
- Precondiciones: ID no existente.
- Pasos:
  1. Abrir `/courses/999999` o equivalente.
  2. Revisar comportamiento de la vista.
- Resultado esperado:
  - Mensaje de error o estado vacío controlado.
  - No hay caída del frontend.

### 4.6 Visualización de recomendaciones

#### Caso AC-19: Generación de recomendaciones con cursos inscritos

- Objetivo: validar el panel de recomendaciones para un alumno con actividad.
- Precondiciones: alumno con cursos y lecciones asociadas.
- Pasos:
  1. Abrir `/recommendations`.
  2. Esperar fin de carga.
  3. Revisar cards y resumen.
- Resultado esperado:
  - El panel muestra recomendaciones y métricas coherentes.
- Evidencia:
  - Captura del panel.
  - Endpoint `GET /api/recomendaciones/{alumnoId}` o `POST` de generación si aplica.
  - SQL:

```sql
SELECT id, mensaje, fecha_generacion, tipo, alumno_id, clase_id
FROM recomendaciones
WHERE alumno_id = 1
ORDER BY fecha_generacion DESC;
```

#### Caso AC-20: Estado vacío de recomendaciones

- Objetivo: validar el caso sin cursos inscritos.
- Precondiciones: alumno sin datos asociados.
- Pasos:
  1. Abrir recomendaciones con alumno sin actividad.
  2. Revisar el mensaje informativo.
- Resultado esperado:
  - Se muestra estado vacío o mensaje de ayuda.
  - No se rompe el layout.

#### Caso AC-21: Error de servicio de recomendaciones

- Objetivo: validar el manejo de error de backend o timeout.
- Precondiciones: fallo en `GET/POST /api/recomendaciones/{alumnoId}`.
- Pasos:
  1. Abrir la vista.
  2. Forzar error del endpoint.
- Resultado esperado:
  - La UI muestra `No se pudieron cargar las recomendaciones. Intenta de nuevo.`
  - El estado de carga termina correctamente.

## 5. VALIDACIONES IMPORTANTES

### 5.1 Persistencia correcta en PostgreSQL

- Verificar que cada operación que modifica datos deje una fila en la tabla correspondiente.
- Validar claves foráneas `alumno_id`, `curso_id`, `clase_id`, `tarea_id` y `entrega_id`.
- Confirmar que las fechas y estados se almacenan en tipos compatibles y luego se recuperan sin cambios inesperados.

### 5.2 Mensajes de error

- Comprobar que los errores de formulario se expresan en lenguaje claro.
- Verificar que los fallos del backend no muestren trazas técnicas en la interfaz.
- Confirmar que el frontend libera el estado de carga tras un error.

### 5.3 Restricciones de acceso

- Validar que un estudiante no pueda ejecutar acciones reservadas a docentes.
- Validar que las vistas docentes no queden accesibles solo por URL si el rol no corresponde.
- Si la aplicación no implementa restricción real todavía, registrar el hallazgo como no conformidad de seguridad funcional.

### 5.4 Integridad de datos

- Verificar que no existan duplicados de inscripción para el mismo alumno y curso.
- Comprobar que la entrega corresponda a una tarea existente.
- Confirmar que una calificación no pueda existir sin entrega relacionada.

### 5.5 Navegación correcta

- Validar coherencia entre la ruta navegada y el contenido renderizado.
- Revisar botones de siguiente/anterior y enlaces a tareas/entregas.
- Verificar retorno al listado de cursos sin pérdida de estado crítico.

### 5.6 Actualización visual del frontend

- Confirmar el reemplazo de skeleton/loading por contenido real.
- Verificar etiquetas de estado como entregada, pendiente, calificada y sin entrega.
- Validar que el cambio de estado tras una acción sea visible sin recargar manualmente toda la aplicación.

### 5.7 Manejo de fallos del backend

- Simular caídas o respuestas 500 para los endpoints principales.
- Verificar que cada componente muestre estado de error o vacío controlado.
- Confirmar que la aplicación no se quede en loading infinito.

### 5.8 Validación de formularios

- Confirmar extensiones aceptadas y tamaño máximo de archivo en entregas.
- Validar obligatoriedad de campos antes de enviar.
- Verificar deshabilitación del botón cuando falta un archivo válido.

### 5.9 Estados de carga

- Validar que cada vista presente `loading` al inicio de la consulta.
- Confirmar que el estado de carga finalice tanto en éxito como en error.
- Verificar que la interfaz no permita acciones inconsistentes mientras carga.

### 5.10 Consistencia de datos entre frontend y backend

- Comparar los campos renderizados con la respuesta JSON real.
- Validar ordenamiento, etiquetas y conteos.
- Revisar que el front no infiera estados distintos a los devueltos por la API.

## 6. EVIDENCIAS

### 6.1 Qué capturas tomar

- Pantalla de listado de cursos con datos cargados.
- Pantalla de detalle de curso con lecciones visibles.
- Pantalla de lección con recursos renderizados.
- Pantalla de listado de tareas y estados.
- Formulario de entrega antes y después del envío.
- Pantalla de calificaciones y promedio.
- Pantalla de recomendaciones con panel cargado.
- Pantallas de error para curso inexistente, archivo inválido y fallo backend.

### 6.2 Qué logs revisar

- Consola del backend Spring Boot.
- Consola del navegador Angular.
- Logs del proxy o servidor de desarrollo si aplica.
- Trazas de errores de red en DevTools cuando el endpoint falle.

### 6.3 Qué consultas SQL ejecutar

- `SELECT * FROM inscripciones WHERE alumno_id = ? AND curso_id = ?;`
- `SELECT * FROM cursos WHERE id = ?;`
- `SELECT * FROM clases WHERE curso_id = ? ORDER BY numero;`
- `SELECT * FROM contenidos WHERE clase_id = ? ORDER BY orden;`
- `SELECT * FROM tareas WHERE clase_id = ? ORDER BY fecha_limite;`
- `SELECT * FROM entregas WHERE alumno_id = ? AND tarea_id = ?;`
- `SELECT * FROM calificaciones WHERE alumno_id = ? ORDER BY fecha_calificacion DESC;`
- `SELECT * FROM recomendaciones WHERE alumno_id = ? ORDER BY fecha_generacion DESC;`

### 6.4 Qué endpoints validar

- `GET /api/cursos`
- `GET /api/cursos/{id}`
- `GET /api/cursos/{id}/clases`
- `GET /api/contenidos/clase/{claseId}`
- `GET /api/tareas/clase/{claseId}`
- `GET /api/tareas/{id}`
- `POST /api/entregas`
- `GET /api/calificaciones/{alumnoId}`
- `GET /api/recomendaciones/{alumnoId}`
- `POST /api/recomendaciones/{alumnoId}`

## 7. AUTOMATIZACIÓN

### 7.1 Estructura recomendada de carpetas

```text
tests/
  acceptance/
    README.md
    cypress/
      e2e/
        enrollment.cy.js
        courses.cy.js
        lessons.cy.js
        assignments.cy.js
        submission.cy.js
        grades.cy.js
        recommendations.cy.js
      fixtures/
        courses.json
        lessons.json
        assignments.json
        recommendations.json
      support/
        commands.js
        e2e.js
    selenium/
      page-objects/
      specs/
      data/
    sql/
      seed-lms.sql
      verify-lms.sql
    evidence/
      screenshots/
      logs/
      network/
```

### 7.2 Convenciones de nombres

- Archivos de prueba: `feature.scenario.expected.cy.js` o `feature.acceptance.spec.ts`.
- IDs de caso: prefijo `AC-` seguido de correlativo estable.
- Capturas: `AC-09_submit_success.png`.
- Logs: `AC-09_backend.log`, `AC-13_console.log`.
- SQL: `AC-09_verify_entregas.sql`.

### 7.3 Ejemplo Cypress

```javascript
describe('Entrega de tareas', () => {
  it('registra una entrega válida y confirma persistencia visual', () => {
    cy.visit('/courses/1/assignments/1/submit');

    cy.contains('Formulario de Entrega').should('be.visible');
    cy.get('input[type="file"]').selectFile('cypress/fixtures/entrega_valida.pdf', { force: true });
    cy.contains('Enviar trabajo').should('not.be.disabled').click();

    cy.contains('Tu trabajo ha sido enviado con éxito').should('be.visible');
  });
});
```

### 7.4 Ejemplo Selenium

```java
@Test
void shouldSubmitAssignmentSuccessfully() {
    driver.get(baseUrl + "/courses/1/assignments/1/submit");
    WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
    fileInput.sendKeys(Paths.get("src/test/resources/entrega_valida.pdf").toAbsolutePath().toString());
    driver.findElement(By.xpath("//button[contains(.,'Enviar trabajo')]")).click();
    assertTrue(driver.getPageSource().contains("Tu trabajo ha sido enviado con éxito"));
}
```

### 7.5 Organización profesional de los tests

- Separar pruebas por funcionalidad de negocio, no por pantalla únicamente.
- Mantener fixtures versionados para cursos, clases, tareas, entregas y recomendaciones.
- Registrar evidencias por caso para trazabilidad académica.
- Usar datos semilla estables y evitarlos cuando la prueba dependa de estado mutable no controlado.

## 8. CRITERIOS DE ACEPTACIÓN

### PASA

- La funcionalidad cumple el resultado esperado sin errores visibles.
- La persistencia en PostgreSQL coincide con la acción ejecutada.
- La UI refleja el estado correcto y los endpoints devuelven datos coherentes.

### FALLA

- La funcionalidad no cumple el resultado esperado.
- Existe divergencia entre frontend, backend y base de datos.
- El mensaje de error no es claro o el flujo deja de responder.

### BLOQUEANTE

- El flujo principal no puede ejecutarse por caída de backend, error de ruta, falta de datos o inconsistencia crítica.
- Se impide validar otros casos dependientes del mismo módulo.

### OBSERVACIÓN

- La funcionalidad pasa, pero con riesgo de usabilidad, consistencia o seguridad.
- Se detecta una mejora técnica o de proceso que no invalida la entrega, pero sí debe documentarse.

## 9. RESULTADO FINAL

### 9.1 Conclusión técnica

La plataforma LMS presenta una base funcional suficiente para validar el flujo académico principal: cursos, lecciones, tareas, entregas, calificaciones y recomendaciones. El backend expone endpoints REST acordes al dominio y el frontend cuenta con rutas, estados de carga y mensajes de error específicos. El sistema permite construir una batería de aceptación realista y ejecutable sobre Angular + Spring Boot + PostgreSQL.

### 9.2 Riesgos encontrados

- El flujo de inscripción no aparece expuesto como endpoint explícito en el análisis del workspace, aunque la tabla `inscripciones` sí existe en PostgreSQL.
- Los archivos de guardas e interceptor del frontend están vacíos, por lo que el control de acceso y la estandarización de errores aún requieren madurez técnica.
- La configuración de seguridad del backend permite acceso amplio a `/api/**`, lo que reduce la eficacia de las restricciones de acceso si el negocio requiere roles estrictos.

### 9.3 Recomendaciones

- Exponer o formalizar el flujo de inscripción para poder ejecutarlo sin depender de seeds manuales.
- Completar guards e interceptor en Angular para centralizar autorización y manejo de errores.
- Añadir pruebas E2E automatizadas en Cypress para los casos AC-03, AC-05, AC-09, AC-12 y AC-14 como mínimo.
- Mantener scripts SQL de seed y verificación por cada funcionalidad crítica.

### 9.4 Cobertura funcional alcanzada

- Cobertura principal: 6 funcionalidades del LMS.
- Cobertura de navegación: rutas de catálogo, detalle, lecciones, tareas, entrega, calificaciones y recomendaciones.
- Cobertura de persistencia: inscripciones, contenidos, tareas, entregas, calificaciones y recomendaciones.
- Cobertura negativa: archivo inválido, archivo pesado, lección inexistente, fallo backend, estado vacío y validación de duplicidad.

### 9.5 Cierre

Con la batería propuesta, el informe queda alineado a una evaluación de aceptación académica y profesional. La entrega puede considerarse documentada de forma sólida, aunque la conformidad final depende de ejecutar los casos sobre el entorno real y registrar evidencias verificables.
