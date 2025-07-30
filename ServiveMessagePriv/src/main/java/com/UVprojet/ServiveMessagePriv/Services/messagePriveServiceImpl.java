

package com.UVprojet.ServiveMessagePriv.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UVprojet.ServiveMessagePriv.Dtos.MessageWebSocketDTO;
import com.UVprojet.ServiveMessagePriv.Dtos.messagePriveDTO;
import com.UVprojet.ServiveMessagePriv.Entities.messagePrivEntity;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;


@Service
public class messagePriveServiceImpl implements messagePriveService {

    @Autowired
    private com.UVprojet.ServiveMessagePriv.Repositories.discussionRepository discussionRepository;

    @Autowired
    private com.UVprojet.ServiveMessagePriv.Repositories.messagePriveRepository messagePriveRepository;

    @Autowired
    private com.UVprojet.ServiveMessagePriv.Mappers.messagePriveMapper messagePriveMapper;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public messagePriveDTO envoyerMessage(messagePriveDTO messagePriveDTO) {
        // Récupérer la discussion pour obtenir le destinataireId
        UUID discussionId = messagePriveDTO.getDiscussionId();
        com.UVprojet.ServiveMessagePriv.Entities.discussionEntity discussion =
            discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion non trouvée"));

        // Remplir automatiquement le destinataireId du message
        messagePriveDTO.setDestinataireId(discussion.getDestinataireId());

        messagePrivEntity messagePrive = messagePriveMapper.toEntity(messagePriveDTO);
        messagePrivEntity savedMessage = messagePriveRepository.save(messagePrive);
        messagePriveDTO result = messagePriveMapper.toDTO(savedMessage);

        // Notification WebSocket
        MessageWebSocketDTO wsMessage = new MessageWebSocketDTO();
        wsMessage.setId(result.getId());
        wsMessage.setDiscussionId(result.getDiscussionId());
        wsMessage.setExpediteurId(result.getExpediteurId());
        wsMessage.setDestinataireId(result.getDestinataireId());
        wsMessage.setContenu(result.getContenu());
        wsMessage.setDateCreation(result.getDateCreation());

        webSocketService.notifierNouveauMessage(wsMessage);
        return result;
    }

    @Override
    public List<messagePriveDTO> obtenirMessagesParDiscussion(UUID discussionId) {
        return messagePriveRepository.findByDiscussionId(discussionId).stream()
                .map(messagePriveMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerMessage(UUID messageId, UUID utilisateurId) {
        messagePrivEntity message = messagePriveRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message non trouvé"));

        if (!message.getExpediteurId().equals(utilisateurId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce message");
        }

        UUID discussionId = message.getDiscussion().getId();
        messagePriveRepository.delete(message);
        
        // Notification WebSocket
        webSocketService.notifierSuppressionMessage(messageId, discussionId);
    }
}