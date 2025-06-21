package com.UV_PROJET.ServicePublication.REPOSITORY;

import com.UV_PROJET.ServicePublication.ENTITES.PublicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {
    List<PublicationEntity> findByUtilisateurId(UUID utilisateurId);
}
