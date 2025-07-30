package com.UVprojet.ServiveMessagePriv.Repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.UVprojet.ServiveMessagePriv.Entities.discussionEntity;

public interface discussionRepository extends JpaRepository<discussionEntity , UUID> {
    java.util.List<discussionEntity> findByExpediteurIdOrDestinataireId(UUID expediteurId, UUID destinataireId);
}
