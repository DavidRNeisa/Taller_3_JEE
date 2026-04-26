package com.example.backend.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    private final Path rootLocation;

    public LocalStorageService(@Value("${app.storage.location:uploads}") String location) {
        this.rootLocation = Paths.get(location).toAbsolutePath().normalize();
        init();
    }

    private void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de almacenamiento", e);
        }
    }

    @Override
    public String guardar(MultipartFile archivo) {

        if (archivo.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        String originalFilename = StringUtils.cleanPath(archivo.getOriginalFilename());

        // Generar nombre único
        String extension = "";

        int index = originalFilename.lastIndexOf('.');
        if (index > 0) {
            extension = originalFilename.substring(index);
        }

        String nuevoNombre = UUID.randomUUID() + extension;

        try {
            Path destino = this.rootLocation.resolve(nuevoNombre);
            Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            // Retornamos la ruta (luego puedes exponerla como URL)
            return destino.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }

    @Override
    public void eliminar(String ruta) {
        try {
            Path archivo = Paths.get(ruta);
            Files.deleteIfExists(archivo);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar archivo", e);
        }
    }
}