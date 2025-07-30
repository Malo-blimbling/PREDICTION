package com.UV_PROJET.ServicePublication.DTO;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class PublicationDTO {
    private long id;
    private UUID utilisateurId;
    private String produitId;
    private String description;
    private Double prix;
    private LocalDateTime datePublication;
    private String region;
}
