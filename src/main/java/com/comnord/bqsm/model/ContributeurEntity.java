package com.comnord.bqsm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contributeurs")
@Data
public class ContributeurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contributeur")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_breve", nullable = false)
    private BreveEntity breveId;

    @Column(name = "name", nullable = false)
    private String name;

}
