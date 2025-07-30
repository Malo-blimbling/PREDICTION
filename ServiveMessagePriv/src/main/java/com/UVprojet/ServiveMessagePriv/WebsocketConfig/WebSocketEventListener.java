package com.UVprojet.ServiveMessagePriv.WebsocketConfig;


import com.UVprojet.ServiveMessagePriv.Services.WebSocketService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
public class WebSocketEventListener {

    @Autowired
    private WebSocketService webSocketService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Nouvelle connexion WebSocket établie");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        UUID utilisateurId = (UUID) headerAccessor.getSessionAttributes().get("utilisateur_id");
        if (utilisateurId != null) {
            webSocketService.notifierDeconnexionUtilisateur(utilisateurId);
            System.out.println("Utilisateur déconnecté: " + utilisateurId);
        }
    }
}