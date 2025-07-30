package com.UV_PROJET.ServicePublication.SERVICE;

import com.UV_PROJET.ServicePublication.DTO.PublicationDTO;
import com.UV_PROJET.ServicePublication.ENTITES.PublicationEntity;
import com.UV_PROJET.ServicePublication.REPOSITORY.PublicationRepository;
import com.UV_PROJET.ServicePublication.MAPPER.PublicationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private RestTemplate restTemplate;

    // URLs via API Gateway (port 9000)
    private static final String USER_SERVICE_URL = "http://localhost:9000/auth/users/";
    private static final String PRODUIT_SERVICE_URL = "http://localhost:9000/api/produits/";

    /**
     * Crée une publication après avoir vérifié l'existence de l'utilisateur et du produit
     * via l'API Gateway. Lève une exception si l'un des deux n'existe pas.
     */
    public PublicationDTO createPublication(PublicationDTO dto) {
        try {
            // Vérification utilisateur via Gateway
            ResponseEntity<String> userResponse = restTemplate.getForEntity(USER_SERVICE_URL + dto.getUtilisateurId(), String.class);
            if (!userResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Utilisateur inexistant");
            }
            // Vérification produit via Gateway
            ResponseEntity<String> produitResponse = restTemplate.getForEntity(PRODUIT_SERVICE_URL + dto.getProduitId(), String.class);
            if (!produitResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Produit inexistant");
            }
            PublicationEntity entity = publicationMapper.toEntity(dto);
            entity.setDatePublication(java.time.LocalDateTime.now());
            PublicationEntity saved = publicationRepository.save(entity);
            return publicationMapper.toDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la publication : " + e.getMessage());
        }
    }

    public List<PublicationDTO> getAllPublications() {
        try {
            return publicationRepository.findAll().stream()
                .map(publicationMapper::toDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des publications : " + e.getMessage());
        }
    }

    public PublicationDTO getPublicationById(Long id) {
        try {
            PublicationEntity entity = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication non trouvée"));
            return publicationMapper.toDTO(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la publication : " + e.getMessage());
        }
    }

    public List<PublicationDTO> getPublicationsByUtilisateurId(UUID utilisateurId) {
        try {
            return publicationRepository.findByUtilisateurId(utilisateurId).stream()
                .map(publicationMapper::toDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des publications par utilisateur : " + e.getMessage());
        }
    }

    public List<PublicationDTO> getPublicationsByRegion(String region) {
        try {
            return publicationRepository.findByRegion(region).stream()
                .map(publicationMapper::toDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des publications par région : " + e.getMessage());
        }
    }

    public void deletePublication(Long id) {
        try {
            publicationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la publication : " + e.getMessage());
        }
    }

    public void deleteAllPublications() {
        publicationRepository.deleteAll();
    }
}
