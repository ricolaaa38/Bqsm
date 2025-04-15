package com.comnord.bqsm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<SubSection> subSections;

    private String comment;
}
