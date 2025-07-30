package com.UVprojet.ServiveMessagePriv.Controllers;


import com.UVprojet.ServiveMessagePriv.Dtos.MessageWebSocketDTO;
import com.UVprojet.ServiveMessagePriv.Services.WebSocketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/chat.envoyer")
    public void envoyerMessage(@Payload MessageWebSocketDTO message) {
        webSocketService.envoyerMessageADiscussion(message.getDiscussionId(), message);
    }

    @MessageMapping("/chat.rejoindre")
    public void rejoindreDiscussion(@Payload MessageWebSocketDTO message, 
                                   SimpMessageHeaderAccessor headerAccessor) {
        // Ajouter l'utilisateur à la session
        headerAccessor.getSessionAttributes().put("utilisateur_id", message.getExpediteurId());
        headerAccessor.getSessionAttributes().put("discussion_id", message.getDiscussionId());
        
        webSocketService.notifierConnexionUtilisateur(
            message.getExpediteurId(), 
            message.getExpediteurNom()
        );
    }
}