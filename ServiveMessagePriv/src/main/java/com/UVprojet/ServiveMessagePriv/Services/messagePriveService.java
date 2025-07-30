package com.UVprojet.ServiveMessagePriv.Services;

import java.util.UUID;

import com.UVprojet.ServiveMessagePriv.Dtos.messagePriveDTO;

import java.util.List;

public interface messagePriveService {
    messagePriveDTO envoyerMessage(messagePriveDTO messagePriveDTO);
    List<messagePriveDTO> obtenirMessagesParDiscussion(UUID discussionId);
    void supprimerMessage(UUID messageId, UUID utilisateurId);
}
