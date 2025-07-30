package com.UV_PROJET.ServicePublication.ENTITES;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;

@Entity
@Table(name = "photo")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "publication_id", nullable = false)
    @ManyToOne
    private PublicationEntity publication;
    private String url;

   /** public long getId() { return id; }
    *public void setId(long id) { this.id = id; }

    *public PublicationEntity getPublication() { return publication; }
    *public void setPublication(PublicationEntity publication) { this.publication = publication; }

    *public String getUrl() { return url; }
    *public void setUrl(String url) { this.url = url; }
    */
}
