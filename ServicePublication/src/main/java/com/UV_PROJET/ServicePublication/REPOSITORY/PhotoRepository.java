package com.UV_PROJET.ServicePublication.REPOSITORY;

import com.UV_PROJET.ServicePublication.ENTITES.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
}
