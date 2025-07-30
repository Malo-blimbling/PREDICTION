package com.UV_PROJET.ServicePublication.ENTITES;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publication")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private UUID utilisateurId;
    private String produitId;
    private String description;
    private Double prix;
    private LocalDateTime datePublication; 
    private String region;

    @OneToMany(mappedBy = "publication", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<PhotoEntity> photos;
}
