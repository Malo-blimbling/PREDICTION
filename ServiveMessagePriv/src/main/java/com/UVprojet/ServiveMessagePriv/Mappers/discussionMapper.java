package com.UVprojet.ServiveMessagePriv.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.UVprojet.ServiveMessagePriv.Dtos.discussionDTO;
import com.UVprojet.ServiveMessagePriv.Entities.discussionEntity;

@Mapper(componentModel = "spring")
public interface discussionMapper {
    discussionDTO toDTO(discussionEntity discussion);
    discussionEntity toEntity(discussionDTO discussionDTO);

    @org.mapstruct.Mapping(target = "id", ignore = true) // Ignore ID for updates
    @org.mapstruct.Mapping(target = "version", ignore = true) // Ignore version for updates to avoid null version issues
    discussionEntity updateFromDTO(discussionDTO dto, @MappingTarget discussionEntity entity);
}
