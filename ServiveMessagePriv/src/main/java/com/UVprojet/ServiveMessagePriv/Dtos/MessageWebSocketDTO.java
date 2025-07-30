
package com.UVprojet.ServiveMessagePriv.Dtos;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageWebSocketDTO {
    private UUID id;
    private UUID discussionId;
    private UUID expediteurId;
    private String expediteurNom;
    private UUID destinataireId;
    private String contenu;
    private LocalDateTime dateCreation;
    private TypeMessage type; // NOUVEAU_MESSAGE, MESSAGE_SUPPRIME, UTILISATEUR_CONNECTE, etc.

    public void setDestinataireId(UUID destinataireId) {
        this.destinataireId = destinataireId;
    }

    public UUID getDestinataireId() {
        return destinataireId;
    }
    
    public enum TypeMessage {
        NOUVEAU_MESSAGE,
        MESSAGE_SUPPRIME,
        UTILISATEUR_CONNECTE,
        UTILISATEUR_DECONNECTE,
        DISCUSSION_MISE_A_JOUR
    }
}