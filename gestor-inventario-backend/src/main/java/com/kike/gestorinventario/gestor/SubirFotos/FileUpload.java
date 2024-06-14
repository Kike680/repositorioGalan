package com.kike.gestorinventario.gestor.SubirFotos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUpload {
    private static final String DIRECTORIO_FOTOS="prodPics/";
    public static String subirFoto(MultipartFile file) {
        if (file.isEmpty()) {
            return null;

        }
        try {
            Path uploadPath = Paths.get(DIRECTORIO_FOTOS);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nomFoto = file.getOriginalFilename();
            String[] partes = nomFoto.split("\\.");
            String nomNuevo = UUID.randomUUID().toString() + UUID.randomUUID().toString() + "." + partes[partes.length - 1];

            Path filePath = uploadPath.resolve(nomNuevo);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return nomNuevo;
        } catch (IOException e) {
            return null;
        }
    }


}
