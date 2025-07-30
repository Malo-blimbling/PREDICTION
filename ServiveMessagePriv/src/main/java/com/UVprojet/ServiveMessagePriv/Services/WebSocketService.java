package com.UVprojet.ServiveMessagePriv.Services;

import java.util.UUID;

import com.UVprojet.ServiveMessagePriv.Dtos.MessageWebSocketDTO;

public interface WebSocketService {
    void notifierNouveauMessage(MessageWebSocketDTO message);
    void notifierSuppressionMessage(UUID messageId, UUID discussionId);
    void notifierConnexionUtilisateur(UUID utilisateurId, String nomUtilisateur);
    void notifierDeconnexionUtilisateur(UUID utilisateurId);
    void envoyerMessageADiscussion(UUID discussionId, MessageWebSocketDTO message);}