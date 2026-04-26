package com.example.backend.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.servlet.function.RequestPredicates.GET;
import org.springframework.web.servlet.function.RouterFunction;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import org.springframework.web.servlet.function.ServerResponse;

import com.example.backend.entities.Calificacion;
import com.example.backend.entities.Contenido;
import com.example.backend.entities.Entrega;
import com.example.backend.entities.Tarea;
import com.example.backend.repositories.CalificacionRepository;
import com.example.backend.repositories.ClaseRepository;
import com.example.backend.repositories.ContenidoRepository;
import com.example.backend.repositories.CursoRepository;
import com.example.backend.repositories.EntregaRepository;
import com.example.backend.repositories.TareaRepository;

@Configuration
public class CourseRoutesConfig {

	@Bean
	public RouterFunction<ServerResponse> courseRoutes(
			CursoRepository cursoRepository,
			ClaseRepository claseRepository,
			ContenidoRepository contenidoRepository,
			TareaRepository tareaRepository,
			EntregaRepository entregaRepository,
			CalificacionRepository calificacionRepository
	) {
		return route(GET("/api/cursos"), request -> ServerResponse.ok().body(
				cursoRepository.findAll().stream()
						.map(curso -> Map.<String, Object>of(
								"id", curso.getId(),
								"titulo", curso.getTitulo(),
								"descripcion", curso.getDescripcion(),
								"totalClases", curso.getTotalClases()
						))
						.toList()
		))
				.andRoute(GET("/api/cursos/{id}"), request -> {
					Long courseId = Long.valueOf(request.pathVariable("id"));
					return cursoRepository.findById(courseId)
							.map(curso -> Map.<String, Object>of(
									"id", curso.getId(),
									"titulo", curso.getTitulo(),
									"descripcion", curso.getDescripcion(),
									"totalClases", curso.getTotalClases()
							))
							.map(course -> ServerResponse.ok().body(course))
							.orElseGet(() -> ServerResponse.notFound().build());
				})
				.andRoute(GET("/api/cursos/{id}/clases"), request -> {
					Long courseId = Long.valueOf(request.pathVariable("id"));

					List<LessonResponse> lessons = claseRepository.findByCursoId(courseId).stream()
							.sorted(Comparator.comparingInt(clase -> {
								Integer numero = clase.getNumero();
								return numero != null ? numero : Integer.MAX_VALUE;
							}))
							.map(clase -> {
								Integer numeroClase = clase.getNumero();
								List<Contenido> contenidos = contenidoRepository.findByClaseIdOrderByOrdenAsc(clase.getId());
								List<Tarea> tareas = tareaRepository.findByClaseId(clase.getId());

								String nextDueDate = tareas.stream()
										.map(Tarea::getFechaLimite)
										.filter(fecha -> fecha != null)
										.min(Comparator.naturalOrder())
										.map(LocalDateTime::toString)
										.orElse(null);

								return new LessonResponse(
										clase.getId(),
										courseId,
										clase.getTitulo(),
										clase.getDescripcion(),
										numeroClase != null ? numeroClase : 0,
										0,
										false,
										contenidos.stream()
												.map(contenido -> {
													Integer ordenContenido = contenido.getOrden();
													return new LessonResourceResponse(
														contenido.getId(),
														contenido.getTipo() == null ? "DOCUMENTO" : contenido.getTipo().name(),
														contenido.getTitulo(),
														contenido.getUrl(),
														ordenContenido != null ? ordenContenido : 0
													);
												})
												.toList(),
										!tareas.isEmpty(),
										nextDueDate
								);
							})
							.toList();

					return ServerResponse.ok().body(lessons);
				})
				.andRoute(GET("/api/cursos/{id}/tareas"), request -> {
					Long courseId = Long.valueOf(request.pathVariable("id"));
					Long alumnoId = parseAlumnoId(request.param("alumnoId").orElse(null));

					List<AssignmentResponse> assignments = buildAssignmentsByCourse(
							courseId,
							alumnoId,
							claseRepository,
							tareaRepository,
							entregaRepository,
							calificacionRepository
					);

					return ServerResponse.ok().body(assignments);
				})
				.andRoute(GET("/api/cursos/{id}/calificaciones"), request -> {
					Long courseId = Long.valueOf(request.pathVariable("id"));
					Long alumnoId = parseAlumnoId(request.param("alumnoId").orElse(null));

					List<GradeResponse> grades = buildAssignmentsByCourse(
							courseId,
							alumnoId,
							claseRepository,
							tareaRepository,
							entregaRepository,
							calificacionRepository
					).stream().map(assignment -> {
						String status = "NOT_DELIVERED";
						if (assignment.grade() != null) {
							status = "GRADED";
						} else if (assignment.submittedDate() != null) {
							status = "PENDING_REVIEW";
						}

						return new GradeResponse(
								assignment.id(),
								assignment.courseId(),
								assignment.lessonId(),
								assignment.title(),
								assignment.dueDate(),
								assignment.submittedDate(),
								assignment.grade(),
								assignment.feedback(),
								status
						);
					}).toList();

					return ServerResponse.ok().body(grades);
				});
	}

	private static Long parseAlumnoId(String rawAlumnoId) {
		if (rawAlumnoId == null || rawAlumnoId.isBlank()) {
			return 1L;
		}

		try {
			return Long.valueOf(rawAlumnoId);
		} catch (NumberFormatException ignored) {
			return 1L;
		}
	}

	private static List<AssignmentResponse> buildAssignmentsByCourse(
			Long courseId,
			Long alumnoId,
			ClaseRepository claseRepository,
			TareaRepository tareaRepository,
			EntregaRepository entregaRepository,
			CalificacionRepository calificacionRepository
	) {
		List<Calificacion> calificacionesAlumno = calificacionRepository.findByAlumnoId(alumnoId);
		Map<Long, Calificacion> calificacionPorTarea = new HashMap<>();
		for (Calificacion calificacion : calificacionesAlumno) {
			if (calificacion.getEntrega() == null || calificacion.getEntrega().getTarea() == null) {
				continue;
			}
			calificacionPorTarea.put(calificacion.getEntrega().getTarea().getId(), calificacion);
		}

		List<AssignmentResponse> assignments = new ArrayList<>();
		claseRepository.findByCursoId(courseId).forEach(clase -> {
			List<Tarea> tareas = tareaRepository.findByClaseId(clase.getId());
			for (Tarea tarea : tareas) {
				Entrega entrega = entregaRepository.findByAlumnoIdAndTareaId(alumnoId, tarea.getId()).orElse(null);
				Calificacion calificacion = calificacionPorTarea.get(tarea.getId());

				String submittedDate = entrega != null && entrega.getFechaEntrega() != null
						? entrega.getFechaEntrega().toString()
						: null;

				String dueDate = tarea.getFechaLimite() != null ? tarea.getFechaLimite().toString() : LocalDateTime.now().toString();
				String deliveryStatus = resolveDeliveryStatus(entrega, tarea.getFechaLimite());
				Integer grade = normalizeScore(calificacion);

				assignments.add(new AssignmentResponse(
						tarea.getId(),
						clase.getId(),
						courseId,
						tarea.getTitulo(),
						tarea.getDescripcion(),
						dueDate,
						submittedDate,
						deliveryStatus,
						grade,
						null,
						entrega != null ? entrega.getArchivoUrl() : null
				));
			}
		});

		return assignments;
	}

	private static Integer normalizeScore(Calificacion calificacion) {
		if (calificacion == null || calificacion.getNota() == null) {
			return null;
		}

		double note = calificacion.getNota();
		if (note <= 5.0d) {
			return (int) Math.round(note * 20.0d);
		}

		return (int) Math.round(Math.max(0, Math.min(100, note)));
	}

	private static String resolveDeliveryStatus(Entrega entrega, LocalDateTime dueDate) {
		if (entrega == null || entrega.getEstadoEntrega() == null) {
			if (dueDate != null && LocalDateTime.now().isAfter(dueDate)) {
				return "NOT_SUBMITTED";
			}
			return "PENDING";
		}

		return switch (entrega.getEstadoEntrega()) {
			case A_TIEMPO -> "SUBMITTED";
			case TARDE -> "LATE";
			case NO_ENTREGO -> "NOT_SUBMITTED";
		};
	}

	private record LessonResourceResponse(
			Long id,
			String type,
			String title,
			String url,
			Integer order
	) {
	}

	private record LessonResponse(
			Long id,
			Long courseId,
			String title,
			String description,
			Integer order,
			Integer progress,
			Boolean completed,
			List<LessonResourceResponse> resources,
			Boolean hasAssignments,
			String assignmentsDue
	) {
	}

	private record AssignmentResponse(
			Long id,
			Long lessonId,
			Long courseId,
			String title,
			String description,
			String dueDate,
			String submittedDate,
			String deliveryStatus,
			Integer grade,
			String feedback,
			String fileUrl
	) {
	}

	private record GradeResponse(
			Long assignmentId,
			Long courseId,
			Long lessonId,
			String assignmentTitle,
			String dueDate,
			String submittedDate,
			Integer score,
			String feedback,
			String status
	) {
	}
}