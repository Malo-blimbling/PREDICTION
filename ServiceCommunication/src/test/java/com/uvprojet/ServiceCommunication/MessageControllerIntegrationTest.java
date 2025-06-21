package com.uvprojet.ServiceCommunication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetMessage() throws Exception {
        // Création d'un message (préparation de la requête POST)
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setPublicationId(200L);
        messageDTO.setUtilisateurId(UUID.randomUUID());
        messageDTO.setContenu("Message d'intégration");
        // La date de création est gérée côté service

        // Envoi de la requête POST pour créer le message
        String postResponse = mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.contenu").value("Message d'intégration"))
                .andReturn().getResponse().getContentAsString();

        // Récupération de l'id du message créé à partir de la réponse
        MessageDTO created = objectMapper.readValue(postResponse, MessageDTO.class);

        // Vérification de la récupération du message par publicationId (GET)
        mockMvc.perform(get("/api/messages/publication/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(created.getId()))
                .andExpect(jsonPath("$[0].contenu").value("Message d'intégration"));
    }

    @Test
    void testDeleteMessage() throws Exception {
        // Création d'un message à supprimer (POST)
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setPublicationId(201L);
        messageDTO.setUtilisateurId(UUID.randomUUID());
        messageDTO.setContenu("Message à supprimer");
        String postResponse = mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        MessageDTO created = objectMapper.readValue(postResponse, MessageDTO.class);

        // Suppression du message créé (DELETE)
        mockMvc.perform(delete("/api/messages/" + created.getId()))
                .andExpect(status().isNoContent());
    }
}
