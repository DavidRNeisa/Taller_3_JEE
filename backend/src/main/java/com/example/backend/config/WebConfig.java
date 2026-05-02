package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.storage.location:uploads}")
    private String storageLocation;

    @Value("${app.content.location:../contenido}")
    private String contentLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path uploadPath = Paths.get(storageLocation).toAbsolutePath().normalize();
        Path contentPath = Paths.get(contentLocation).toAbsolutePath().normalize();

        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + uploadPath + "/");

        registry.addResourceHandler("/contenido/**")
                .addResourceLocations("file:" + contentPath + "/");
    }
}