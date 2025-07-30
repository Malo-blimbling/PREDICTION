package com.uvprojet.ServiceCommunication;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final RestTemplate restTemplate = new RestTemplate();

   
    private final String USER_SERVICE_URL = "http://localhost:9000/auth/users/";
    private final String PUBLICATION_SERVICE_URL = "http://localhost:9000/api/publications/";

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    /**
     * Crée un message après avoir vérifié l'existence de l'utilisateur et de la publication
     * @param messageDTO DTO du message à créer
     * @return MessageDTO créé
     * @throws ResponseStatusException 404 si utilisateur ou publication non trouvés
     */
    public MessageDTO createMessage(MessageDTO messageDTO) {
        // Vérification de l'existence de l'utilisateur via appel REST
        try {
            restTemplate.getForEntity(USER_SERVICE_URL + messageDTO.getUtilisateurId(), Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
        }
        // Vérification de l'existence de la publication via appel REST
        try {
            restTemplate.getForEntity(PUBLICATION_SERVICE_URL + messageDTO.getPublicationId(), Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publication non trouvée");
        }
        // Mapping DTO -> entité et sauvegarde
        Message message = messageMapper.toEntity(messageDTO);
        message.setDateCreation(LocalDateTime.now());
        Message saved = messageRepository.save(message);
        return messageMapper.toDTO(saved);
    }

    /**
     * Récupère tous les messages liés à une publication
     * @param publicationId id de la publication
     * @return liste de MessageDTO
     */
    public List<MessageDTO> getMessagesByPublicationId(Long publicationId) {
        return messageRepository.findByPublicationId(publicationId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Met à jour le contenu d'un message existant
     * @param id id du message à mettre à jour
     * @param messageDTO données à mettre à jour
     * @return MessageDTO mis à jour (Optional)
     */
    public Optional<MessageDTO> updateMessage(Long id, MessageDTO messageDTO) {
        return messageRepository.findById(id).map(existing -> {
            existing.setContenu(messageDTO.getContenu());
            Message updated = messageRepository.save(existing);
            return messageMapper.toDTO(updated);
        });
    }

    /**
     * Supprime un message par son id
     * @param id id du message à supprimer
     */
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    /**
     * Récupère un message par son id
     * @param id id du message
     * @return MessageDTO si trouvé
     * @throws ResponseStatusException 404 si non trouvé
     */
    public MessageDTO getMessageById(Long id) {
        return messageRepository.findById(id)
                .map(messageMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message non trouvé"));
    }
}
