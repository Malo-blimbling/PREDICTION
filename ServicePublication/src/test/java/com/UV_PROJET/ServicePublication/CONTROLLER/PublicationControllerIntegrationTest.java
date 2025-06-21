package com.UV_PROJET.ServicePublication.CONTROLLER;

import com.UV_PROJET.ServicePublication.DTO.PublicationDTO;
import com.UV_PROJET.ServicePublication.REPOSITORY.PublicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.ExpectedCount;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test d'intégration du contrôleur PublicationController avec MockMvc.
 * Vérifie le comportement des endpoints REST en conditions réelles.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PublicationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    /**
     * Teste la création puis la récupération d'une publication.
     */
    @Test
    void testCreateAndGetPublication() throws Exception {
        PublicationDTO dto = new PublicationDTO();
        dto.setUtilisateurId(UUID.randomUUID());
        dto.setProduitId("PROD-001");
        dto.setDescription("Integration Test");
        dto.setPrix(50.0);
        // Mock User et Produit via Gateway
        mockServer.expect(ExpectedCount.once(), requestTo("http://localhost:9000/api/users/" + dto.getUtilisateurId()))
                .andRespond(withStatus(HttpStatus.OK));
        mockServer.expect(ExpectedCount.once(), requestTo("http://localhost:9000/api/produits/" + dto.getProduitId()))
                .andRespond(withStatus(HttpStatus.OK));
        // Création
        String response = mockMvc.perform(post("/api/publications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PublicationDTO created = objectMapper.readValue(response, PublicationDTO.class);
        // Lecture
        mockMvc.perform(get("/api/publications/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Integration Test"));
        mockServer.verify();
    }

    /**
     * Teste la récupération de toutes les publications.
     */
    @Test
    void testGetAllPublications() throws Exception {
        mockMvc.perform(get("/api/publications"))
                .andExpect(status().isOk());
    }

    /**
     * Teste la récupération d'une publication inexistante (404 attendu).
     */
    @Test
    void testGetPublicationByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/publications/999999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Teste la suppression d'une publication.
     */
    @Test
    void testDeletePublication() throws Exception {
        PublicationDTO dto = new PublicationDTO();
        dto.setUtilisateurId(UUID.randomUUID());
        dto.setProduitId("PROD-002");
        dto.setDescription("To Delete");
        dto.setPrix(20.0);
        // Mock User et Produit via Gateway
        mockServer.expect(ExpectedCount.once(), requestTo("http://localhost:9000/api/users/" + dto.getUtilisateurId()))
                .andRespond(withStatus(HttpStatus.OK));
        mockServer.expect(ExpectedCount.once(), requestTo("http://localhost:9000/api/produits/" + dto.getProduitId()))
                .andRespond(withStatus(HttpStatus.OK));
        String response = mockMvc.perform(post("/api/publications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        PublicationDTO created = objectMapper.readValue(response, PublicationDTO.class);
        mockMvc.perform(delete("/api/publications/" + created.getId()))
                .andExpect(status().isOk());
        mockServer.verify();
    }
}
