package com.UV_PROJET.ServicePublication.CONTROLLER;

import com.UV_PROJET.ServicePublication.DTO.PublicationDTO;
import com.UV_PROJET.ServicePublication.SERVICE.PublicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {
    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @PostMapping
    public PublicationDTO createPublication(@RequestBody PublicationDTO dto) {
        return publicationService.createPublication(dto);
    }

    @GetMapping
    public List<PublicationDTO> getAllPublications() {
        return publicationService.getAllPublications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublicationById(@PathVariable Long id) {
        try {
            PublicationDTO publication = publicationService.getPublicationById(id);
            return ResponseEntity.ok(publication);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<PublicationDTO> getPublicationsByUtilisateurId(@PathVariable UUID utilisateurId) {
        return publicationService.getPublicationsByUtilisateurId(utilisateurId);
    }

    @DeleteMapping("/{id}")
    public void deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
    }
}
