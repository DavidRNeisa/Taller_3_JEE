package com.example.backend.controllers.auth;

import com.example.backend.entities.Alumno;
import com.example.backend.repositories.AlumnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AlumnoRepository alumnoRepository;

    @PostMapping("/login")
    public Alumno login(@RequestParam String email) {

        Optional<Alumno> alumno = alumnoRepository.findByEmail(email);

        if (alumno.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return alumno.get();
    }
}