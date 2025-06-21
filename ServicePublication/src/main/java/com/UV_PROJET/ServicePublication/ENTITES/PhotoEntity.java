package com.UV_PROJET.ServicePublication.ENTITES;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "publication_id", nullable = false)
    @ManyToOne
    private PublicationEntity publication;
    private String url;  
}
