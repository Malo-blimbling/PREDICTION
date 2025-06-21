package com.UV_PROJET.ServicePublication.SERVICE;

import com.UV_PROJET.ServicePublication.DTO.PublicationDTO;
import com.UV_PROJET.ServicePublication.ENTITES.PublicationEntity;
import com.UV_PROJET.ServicePublication.REPOSITORY.PublicationRepository;
import com.UV_PROJET.ServicePublication.MAPPER.PublicationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitaire du service PublicationService avec Mockito.
 * Vérifie la logique métier sans accès à la base réelle.
 */
class PublicationServiceTest {
    @Mock
    private PublicationRepository publicationRepository;
    @Mock
    private PublicationMapper publicationMapper;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private PublicationService publicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisation des mocks
    }

    /**
     * Teste la création d'une publication avec mocks.
     */
    @Test
    void testCreatePublication() {
        PublicationDTO dto = new PublicationDTO();
        dto.setUtilisateurId(UUID.randomUUID());
        dto.setProduitId("PROD-001");
        dto.setDescription("Test");
        dto.setPrix(10.0);
        PublicationEntity entity = new PublicationEntity();
        entity.setDescription("Test");
        entity.setPrix(10.0);
        PublicationEntity saved = new PublicationEntity();
        saved.setId(1L);
        saved.setDescription("Test");
        saved.setPrix(10.0);
        saved.setDatePublication(LocalDateTime.now());
        PublicationDTO resultDto = new PublicationDTO();
        resultDto.setId(1L);
        resultDto.setDescription("Test");
        resultDto.setPrix(10.0);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(new org.springframework.http.ResponseEntity<>("{}", org.springframework.http.HttpStatus.OK));
        when(publicationMapper.toEntity(dto)).thenReturn(entity);
        when(publicationRepository.save(any(PublicationEntity.class))).thenReturn(saved);
        when(publicationMapper.toDTO(saved)).thenReturn(resultDto);
        PublicationDTO result = publicationService.createPublication(dto);
        assertEquals("Test", result.getDescription());
        assertNotNull(result.getId());
    }

    /**
     * Teste la récupération de toutes les publications.
     */
    @Test
    void testGetAllPublications() {
        PublicationEntity entity = new PublicationEntity();
        entity.setId(1L);
        entity.setDescription("Test");
        PublicationDTO dto = new PublicationDTO();
        dto.setId(1L);
        dto.setDescription("Test");
        when(publicationRepository.findAll()).thenReturn(Collections.singletonList(entity));
        when(publicationMapper.toDTO(entity)).thenReturn(dto);
        List<PublicationDTO> result = publicationService.getAllPublications();
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getDescription());
    }

    /**
     * Teste la récupération d'une publication par son id.
     */
    @Test
    void testGetPublicationById() {
        PublicationEntity entity = new PublicationEntity();
        entity.setId(1L);
        entity.setDescription("Test");
        PublicationDTO dto = new PublicationDTO();
        dto.setId(1L);
        dto.setDescription("Test");
        when(publicationRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(publicationMapper.toDTO(entity)).thenReturn(dto);
        PublicationDTO result = publicationService.getPublicationById(1L);
        assertEquals("Test", result.getDescription());
    }

    /**
     * Teste la récupération des publications par utilisateur.
     */
    @Test
    void testGetPublicationsByUtilisateurId() {
        UUID userId = UUID.randomUUID();
        PublicationEntity entity = new PublicationEntity();
        entity.setId(1L);
        entity.setUtilisateurId(userId);
        entity.setDescription("Test");
        PublicationDTO dto = new PublicationDTO();
        dto.setId(1L);
        dto.setUtilisateurId(userId);
        dto.setDescription("Test");
        when(publicationRepository.findByUtilisateurId(userId)).thenReturn(Collections.singletonList(entity));
        when(publicationMapper.toDTO(entity)).thenReturn(dto);
        List<PublicationDTO> result = publicationService.getPublicationsByUtilisateurId(userId);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUtilisateurId());
    }

    /**
     * Teste la suppression d'une publication.
     */
    @Test
    void testDeletePublication() {
        doNothing().when(publicationRepository).deleteById(1L);
        assertDoesNotThrow(() -> publicationService.deletePublication(1L));
        verify(publicationRepository, times(1)).deleteById(1L);
    }
}
