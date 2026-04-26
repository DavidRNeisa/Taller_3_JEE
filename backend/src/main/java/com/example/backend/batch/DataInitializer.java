package com.example.backend.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final BatchLoaderJob batchLoaderJob;

    @Bean
    CommandLineRunner initData() {
        return args -> batchLoaderJob.ejecutar();
    }
}