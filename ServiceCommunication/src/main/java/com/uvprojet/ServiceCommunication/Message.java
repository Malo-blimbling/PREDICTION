package com.uvprojet.ServiceCommunication;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long publicationId;
    private UUID utilisateurId;
    private String contenu;
    private LocalDateTime dateCreation;

    // Méthodes explicites pour garantir la compilation
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    public String getContenu() {
        return contenu;
    }
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPublicationId() {
        return publicationId;
    }
    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }
    public UUID getUtilisateurId() {
        return utilisateurId;
    }
    public void setUtilisateurId(UUID utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
}
