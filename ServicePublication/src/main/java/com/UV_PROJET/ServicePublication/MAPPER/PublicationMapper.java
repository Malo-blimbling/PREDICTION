package com.UV_PROJET.ServicePublication.MAPPER;

import com.UV_PROJET.ServicePublication.ENTITES.PublicationEntity;
import com.UV_PROJET.ServicePublication.DTO.PublicationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    PublicationDTO toDTO(PublicationEntity entity);
    PublicationEntity toEntity(PublicationDTO dto);
}
