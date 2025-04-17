package com.comnord.bqsm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "commentaires")
@Data
public class CommentaireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commentaire")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_breve", nullable = false)
    private BreveEntity breveId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "redacteur", nullable = false)
    private String redacteur;

    @Column(name = "objet", nullable = false)
    private String objet;

    @Column(name = "commentaire", nullable = false, columnDefinition = "TEXT")
    private String commentaire;
}
