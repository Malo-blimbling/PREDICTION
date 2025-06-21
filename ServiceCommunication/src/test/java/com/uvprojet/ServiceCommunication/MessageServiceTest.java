package com.uvprojet.ServiceCommunication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageMapper messageMapper;
    @InjectMocks
    private MessageService messageService;

    private Message message;
    private MessageDTO messageDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        message = new Message(1L, 100L, UUID.randomUUID(), "Test contenu", LocalDateTime.now());
        messageDTO = new MessageDTO(1L, 100L, message.getUtilisateurId(), "Test contenu", message.getDateCreation());
    }

    @Test
    void testCreateMessage() {
        // Vérifie que la création d'un message fonctionne et retourne le bon contenu
        when(messageMapper.toEntity(any(MessageDTO.class))).thenReturn(message);
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageMapper.toDTO(any(Message.class))).thenReturn(messageDTO);

        MessageDTO result = messageService.createMessage(messageDTO);
        assertNotNull(result);
        assertEquals(messageDTO.getContenu(), result.getContenu());
    }

    @Test
    void testGetMessagesByPublicationId() {
        // Vérifie que la récupération des messages par publicationId retourne la bonne liste
        when(messageRepository.findByPublicationId(100L)).thenReturn(Arrays.asList(message));
        when(messageMapper.toDTO(any(Message.class))).thenReturn(messageDTO);

        List<MessageDTO> result = messageService.getMessagesByPublicationId(100L);
        assertEquals(1, result.size());
        assertEquals(messageDTO.getContenu(), result.get(0).getContenu());
    }

    @Test
    void testUpdateMessage() {
        // Vérifie que la mise à jour d'un message existant fonctionne et retourne le bon contenu
        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageMapper.toDTO(any(Message.class))).thenReturn(messageDTO);

        Optional<MessageDTO> result = messageService.updateMessage(1L, messageDTO);
        assertTrue(result.isPresent());
        assertEquals(messageDTO.getContenu(), result.get().getContenu());
    }

    @Test
    void testDeleteMessage() {
        // Vérifie que la suppression d'un message ne lève pas d'exception et appelle le repository
        doNothing().when(messageRepository).deleteById(1L);
        assertDoesNotThrow(() -> messageService.deleteMessage(1L));
        verify(messageRepository, times(1)).deleteById(1L);
    }
}
