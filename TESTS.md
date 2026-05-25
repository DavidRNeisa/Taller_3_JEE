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
   - Implementar pruebas automatizadas para las funcionalidades clave utilizando frameworks como:
     - **JUnit** para el backend en Spring Boot.
     - **Selenium** para pruebas de interfaz en Angular.
     - **xUnit** para la aplicación en .NET.

### 6. **Entrega**
   - Asegurarse de que las pruebas sean reproducibles y estén documentadas.
   - Proveer scripts o configuraciones necesarias para ejecutar las pruebas.
