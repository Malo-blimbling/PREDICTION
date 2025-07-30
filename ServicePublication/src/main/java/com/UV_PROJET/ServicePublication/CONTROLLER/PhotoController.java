package com.UV_PROJET.ServicePublication.CONTROLLER;

import com.UV_PROJET.ServicePublication.DTO.PhotoDTO;
import com.UV_PROJET.ServicePublication.SERVICE.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    private static final String UPLOAD_DIR = "uploads/photos/";

    @PostMapping
    public PhotoDTO addPhoto(@RequestBody PhotoDTO dto) {
        return photoService.addPhoto(dto);
    }

    @GetMapping("/publication/{publicationId}")
    public List<PhotoDTO> getPhotosByPublication(@PathVariable Long publicationId) {
        return photoService.getPhotosByPublication(publicationId);
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<PhotoDTO> uploadPhoto(@RequestParam("file") MultipartFile file,
                                                @RequestParam("publicationId") Long publicationId) {
        try {
            // Créer le dossier si besoin
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Générer un nom unique
            String ext = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename());
            String filename = java.util.UUID.randomUUID() + (ext != null ? "." + ext : "");
            Path filePath = java.nio.file.Paths.get(UPLOAD_DIR, filename);
            java.nio.file.Files.write(filePath, file.getBytes());


            // Construire l'URL locale propre (toujours /uploads/photos/nom-image.jpg)
            String url = "/uploads/photos/" + filename;

            PhotoDTO dto = new PhotoDTO();
            dto.setPublicationId(publicationId);
            dto.setUrl(url);
            PhotoDTO saved = photoService.addPhoto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
