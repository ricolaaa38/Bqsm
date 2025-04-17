package com.comnord.bqsm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "breves")
@Data
public class BreveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_breve")
    private int id;

    @Column(name = "bqsm_num", nullable = false)
    private String bqsmNumb;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "categorie", nullable = false)
    private String categorie;

    @Column(name = "zone")
    private String zone;

    @Column(name = "pays")
    private String pays;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "contenu", columnDefinition = "TEXT")
    private String contenu;

    @JsonIgnore
    @OneToMany(mappedBy = "breveId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PictureEntity> pictures;

    @JsonIgnore
    @OneToMany(mappedBy = "breveId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LinkEntity> links;

    @JsonIgnore
    @OneToMany(mappedBy = "breveId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IntervenantEntity> intervenants;

    @JsonIgnore
    @OneToMany(mappedBy = "breveId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContributeurEntity> contributeurs;

    @JsonIgnore
    @OneToOne(mappedBy = "breveId", cascade = CascadeType.ALL, orphanRemoval = true)
    private BreveViewTrackerEntity breveViewTracker;

    @JsonIgnore
    @OneToMany(mappedBy = "breveId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentaireEntity> commentaires;



}
