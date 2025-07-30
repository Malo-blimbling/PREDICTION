package com.UVprojet.ServiveMessagePriv.Entities;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "discussions")
@Data
public class discussionEntity {

    @Id
    @GeneratedValue
    private UUID id;


    @Column
    private UUID expediteurId; //a parti de son id on recupere son nom et prenom

    @Column(nullable = false)
    private UUID destinataireId; // nouvel attribut pour la confidentialité

    @Column
    private String sujet;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Column
    private String statut;

    @Version
    private Long version; // Champ pour le verrouillage optimiste
}
    


