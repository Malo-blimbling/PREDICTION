package com.UV_PROJET.ServicePublication.DTO;

import com.UV_PROJET.ServicePublication.ENTITES.PublicationEntity;

public class PhotoDTO {
    private Long id;
    private Long publicationId;
    private String url;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPublicationId() { return publicationId; }
    public void setPublicationId(Long publicationId) { this.publicationId = publicationId; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
