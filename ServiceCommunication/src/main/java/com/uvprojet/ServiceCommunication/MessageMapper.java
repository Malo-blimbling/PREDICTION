package com.uvprojet.ServiceCommunication;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "publicationId", source = "publicationId"),
        @Mapping(target = "utilisateurId", source = "utilisateurId"),
        @Mapping(target = "contenu", source = "contenu"),
        @Mapping(target = "dateCreation", source = "dateCreation")
    })
    MessageDTO toDTO(Message message);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "publicationId", source = "publicationId"),
        @Mapping(target = "utilisateurId", source = "utilisateurId"),
        @Mapping(target = "contenu", source = "contenu"),
        @Mapping(target = "dateCreation", source = "dateCreation")
    })
    Message toEntity(MessageDTO messageDTO);
}
