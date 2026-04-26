package com.example.backend.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String guardar(MultipartFile archivo);

    void eliminar(String ruta);

}