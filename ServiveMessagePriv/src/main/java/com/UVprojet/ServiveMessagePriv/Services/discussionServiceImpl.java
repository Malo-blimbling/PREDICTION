package com.UVprojet.ServiveMessagePriv.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.UVprojet.ServiveMessagePriv.Dtos.discussionDTO;
import com.UVprojet.ServiveMessagePriv.Dtos.UserDTO;
import com.UVprojet.ServiveMessagePriv.Entities.discussionEntity;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class discussionServiceImpl implements discussionService {

    @Autowired
    private com.UVprojet.ServiveMessagePriv.Repositories.discussionRepository discussionRepository;

    @Autowired
    private com.UVprojet.ServiveMessagePriv.Mappers.discussionMapper discussionMapper;

    @Autowired
    private RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:9000/api/auth/users/";

    @Override
    public discussionDTO creerDiscussion(discussionDTO discussionDTO) {
        if (discussionDTO.getId() != null) {
            throw new IllegalArgumentException("L'id doit être null pour la création d'une nouvelle discussion");
        }
        discussionEntity discussion = discussionMapper.toEntity(discussionDTO);
        // Nouvelle discussion, initialiser la date de création
        discussion.setDateCreation(java.time.LocalDateTime.now());
        discussionEntity savedDiscussion = discussionRepository.save(discussion);
        return discussionMapper.toDTO(savedDiscussion);
    }


    // Nouvelle méthode pour filtrer par utilisateur
    public List<discussionDTO> obtenirDiscussionsPourUtilisateur(UUID utilisateurId) {
        return discussionRepository.findByExpediteurIdOrDestinataireId(utilisateurId, utilisateurId)
                .stream()
                .map(discussionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<discussionDTO> obtenirDiscussions() {
        return discussionRepository.findAll().stream()
                .map(discussionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(String id) {
        String url = USER_SERVICE_URL + id;
        return restTemplate.getForObject(url, UserDTO.class);
    }

    @Transactional
    public void modifierDiscussion(UUID id, discussionDTO modifications) {
        discussionEntity discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discussion non trouvée"));

        // Appliquez les modifications
        discussionMapper.updateFromDTO(modifications, discussion);

        discussionRepository.save(discussion); // Merge automatique
    }
}
