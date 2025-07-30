package com.UVprojet.ServiveMessagePriv.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.UVprojet.ServiveMessagePriv.Entities.messagePrivEntity;

import java.util.UUID;
import java.util.List;

public interface messagePriveRepository extends JpaRepository<messagePrivEntity, UUID> {
    List<messagePrivEntity> findByDiscussionId(UUID discussionId);
}

