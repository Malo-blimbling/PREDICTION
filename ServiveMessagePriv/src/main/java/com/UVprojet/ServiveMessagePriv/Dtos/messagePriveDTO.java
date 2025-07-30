package com.UVprojet.ServiveMessagePriv.Dtos;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class messagePriveDTO {
    private UUID id;
    private UUID discussionId;
    private UUID expediteurId;
    private UUID destinataireId;
    private String contenu;
    private LocalDateTime dateCreation;
}
