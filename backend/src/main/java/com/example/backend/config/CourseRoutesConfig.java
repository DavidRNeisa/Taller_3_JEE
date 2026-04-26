package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.servlet.function.RequestPredicates.GET;
import org.springframework.web.servlet.function.RouterFunction;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import org.springframework.web.servlet.function.ServerResponse;

import com.example.backend.dtos.response.CursoResponse;
import com.example.backend.repositories.CursoRepository;

@Configuration
public class CourseRoutesConfig {

	@Bean
	public RouterFunction<ServerResponse> courseRoutes(CursoRepository cursoRepository) {
		return route(GET("/api/cursos"), request -> ServerResponse.ok().body(
				cursoRepository.findAll().stream()
						.map(curso -> CursoResponse.builder()
								.id(curso.getId())
								.titulo(curso.getTitulo())
								.descripcion(curso.getDescripcion())
								.totalClases(curso.getTotalClases())
								.build())
						.toList()
		))
				.andRoute(GET("/api/cursos/{id}"), request -> {
					Long courseId = Long.valueOf(request.pathVariable("id"));
					return cursoRepository.findById(courseId)
							.map(curso -> CursoResponse.builder()
									.id(curso.getId())
									.titulo(curso.getTitulo())
									.descripcion(curso.getDescripcion())
									.totalClases(curso.getTotalClases())
									.build())
							.map(course -> ServerResponse.ok().body(course))
							.orElseGet(() -> ServerResponse.notFound().build());
					});
	}
}