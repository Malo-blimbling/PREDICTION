package com.UVprojet.ServiveMessagePriv.Dtos;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class discussionDTO {
    private UUID id;
    private UUID expediteurId; // a partir de son id on recupere son nom et prenom
    private UUID destinataireId; // nouvel attribut pour la confidentialité
    private String sujet; // Sujet de la discussion
    private LocalDateTime dateCreation;
    private String statut;
}