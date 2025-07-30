package com.UVprojet.ServiveMessagePriv.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.UVprojet.ServiveMessagePriv.Dtos.MessageWebSocketDTO;

import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void notifierNouveauMessage(MessageWebSocketDTO message) {
        message.setType(MessageWebSocketDTO.TypeMessage.NOUVEAU_MESSAGE);
        // Notifier uniquement l'expéditeur et le destinataire sur des canaux privés
        if (message.getExpediteurId() != null) {
            messagingTemplate.convertAndSend("/queue/messages/" + message.getExpediteurId(), message);
        }
        if (message.getDestinataireId() != null && !message.getDestinataireId().equals(message.getExpediteurId())) {
            messagingTemplate.convertAndSend("/queue/messages/" + message.getDestinataireId(), message);
        }
    }

    @Override
    public void notifierSuppressionMessage(UUID messageId, UUID discussionId) {
        MessageWebSocketDTO notification = new MessageWebSocketDTO();
        notification.setId(messageId);
        notification.setDiscussionId(discussionId);
        notification.setType(MessageWebSocketDTO.TypeMessage.MESSAGE_SUPPRIME);
        notification.setDateCreation(LocalDateTime.now());
        
        envoyerMessageADiscussion(discussionId, notification);
    }

    @Override
    public void notifierConnexionUtilisateur(UUID utilisateurId, String nomUtilisateur) {
        MessageWebSocketDTO notification = new MessageWebSocketDTO();
        notification.setExpediteurId(utilisateurId);
        notification.setExpediteurNom(nomUtilisateur);
        notification.setType(MessageWebSocketDTO.TypeMessage.UTILISATEUR_CONNECTE);
        notification.setDateCreation(LocalDateTime.now());
        
        // Diffuser à tous les utilisateurs connectés
        messagingTemplate.convertAndSend("/topic/utilisateurs", notification);
    }

    @Override
    public void notifierDeconnexionUtilisateur(UUID utilisateurId) {
        MessageWebSocketDTO notification = new MessageWebSocketDTO();
        notification.setExpediteurId(utilisateurId);
        notification.setType(MessageWebSocketDTO.TypeMessage.UTILISATEUR_DECONNECTE);
        notification.setDateCreation(LocalDateTime.now());
        
        messagingTemplate.convertAndSend("/topic/utilisateurs", notification);
    }

    @Override
    public void envoyerMessageADiscussion(UUID discussionId, MessageWebSocketDTO message) {
        // Envoyer le message à tous les participants de la discussion
        messagingTemplate.convertAndSend("/topic/discussion/" + discussionId, message);
    }
}
