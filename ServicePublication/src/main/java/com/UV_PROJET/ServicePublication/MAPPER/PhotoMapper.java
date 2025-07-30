package com.UV_PROJET.ServicePublication.MAPPER;

import com.UV_PROJET.ServicePublication.DTO.PhotoDTO;
import com.UV_PROJET.ServicePublication.ENTITES.PhotoEntity;
import com.UV_PROJET.ServicePublication.ENTITES.PublicationEntity;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapper {
    public PhotoDTO toDTO(PhotoEntity entity) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(entity.getId());
        dto.setUrl(entity.getUrl());
        if (entity.getPublication() != null) {
            dto.setPublicationId(entity.getPublication().getId());
        }
        return dto;
    }

    public PhotoEntity toEntity(PhotoDTO dto, PublicationEntity publication) {
        PhotoEntity entity = new PhotoEntity();
        // Ne pas setter l'id si null (création)
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setUrl(dto.getUrl());
        entity.setPublication(publication);
        return entity;
    }
}
