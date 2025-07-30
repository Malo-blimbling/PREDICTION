package com.UVprojet.ServiveMessagePriv.Mappers;
import org.mapstruct.Mapper;
import com.UVprojet.ServiveMessagePriv.Dtos.messagePriveDTO;
import com.UVprojet.ServiveMessagePriv.Entities.messagePrivEntity;

@Mapper(componentModel = "spring")
public interface messagePriveMapper {
    messagePriveDTO toDTO(messagePrivEntity messagePrive);
    messagePrivEntity  toEntity(messagePriveDTO messagePriveDTO);
}

