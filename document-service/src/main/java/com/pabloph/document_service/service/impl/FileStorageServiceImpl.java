package com.pabloph.document_service.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pabloph.document_service.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path storageLocation;

    // Define y crea la carpeta donde se guardarán los archivos.
    public FileStorageServiceImpl(
            @Value("${app.storage.location:uploads}") String storageLocation
    ) {
        this.storageLocation = Paths.get(storageLocation)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.storageLocation);
        } catch (IOException e) {
            throw new RuntimeException(
                    "No se pudo crear la carpeta de almacenamiento",
                    e
            );
        }
    }

    // Guarda físicamente el archivo y devuelve su ruta.
    @Override
    public String store(MultipartFile file) {

        validateFile(file);

        String originalName =
                StringUtils.cleanPath(file.getOriginalFilename());

        String extension = getExtension(originalName);

        String newFileName =
                UUID.randomUUID() + extension;

        Path targetLocation =
                storageLocation.resolve(newFileName).normalize();

        // Evita que un archivo pueda guardarse fuera de /uploads.
        if (!targetLocation.startsWith(storageLocation)) {
            throw new RuntimeException("Ruta de archivo inválida");
        }

        try {

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return targetLocation.toString();

        } catch (IOException e) {

            throw new RuntimeException(
                    "No se pudo guardar el archivo",
                    e
            );
        }
    }

    // Busca y carga el archivo guardado.
    @Override
    public Resource load(String filePath) {

        try {

            Path path = Paths.get(filePath)
                    .toAbsolutePath()
                    .normalize();

            Resource resource =
                    new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new RuntimeException(
                        "El archivo no existe"
                );
            }

            if (!resource.isReadable()) {
                throw new RuntimeException(
                        "El archivo no puede ser leído"
                );
            }

            return resource;

        } catch (MalformedURLException e) {

            throw new RuntimeException(
                    "Ruta del archivo inválida",
                    e
            );
        }
    }

    // Elimina físicamente el archivo.
    @Override
    public void delete(String filePath) {

        try {

            Path path = Paths.get(filePath)
                    .toAbsolutePath()
                    .normalize();

            Files.deleteIfExists(path);

        } catch (IOException e) {

            throw new RuntimeException(
                    "No se pudo eliminar el archivo",
                    e
            );
        }
    }

    // Verifica que exista el archivo y que sea PDF.
    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "El archivo está vacío"
            );
        }

        if (!"application/pdf".equalsIgnoreCase(
                file.getContentType()
        )) {
            throw new RuntimeException(
                    "Solo se permiten archivos PDF"
            );
        }
    }

    // Obtiene la extensión del archivo, por ejemplo ".pdf".
    private String getExtension(String fileName) {

        if (fileName == null) {
            return "";
        }

        int index = fileName.lastIndexOf(".");

        if (index == -1) {
            return "";
        }

        return fileName.substring(index);
    }
}