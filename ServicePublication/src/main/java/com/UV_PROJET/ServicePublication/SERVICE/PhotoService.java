package com.UV_PROJET.ServicePublication.SERVICE;

import com.UV_PROJET.ServicePublication.DTO.PhotoDTO;
import com.UV_PROJET.ServicePublication.ENTITES.PhotoEntity;
import com.UV_PROJET.ServicePublication.ENTITES.PublicationEntity;
import com.UV_PROJET.ServicePublication.REPOSITORY.PhotoRepository;
import com.UV_PROJET.ServicePublication.REPOSITORY.PublicationRepository;
import com.UV_PROJET.ServicePublication.MAPPER.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private PhotoMapper photoMapper;

    public PhotoDTO addPhoto(PhotoDTO dto) {
        PublicationEntity publication = publicationRepository.findById(dto.getPublicationId())
            .orElseThrow(() -> new RuntimeException("Publication non trouvée"));
        PhotoEntity entity = photoMapper.toEntity(dto, publication);
        PhotoEntity saved = photoRepository.save(entity);
        return photoMapper.toDTO(saved);
    }

    public List<PhotoDTO> getPhotosByPublication(Long publicationId) {
        return photoRepository.findAll().stream()
            .filter(photo -> photo.getPublication().getId() == publicationId)
            .map(photoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }
}
