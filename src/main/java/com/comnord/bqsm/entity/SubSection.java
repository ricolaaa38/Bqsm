package com.comnord.bqsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SubSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subtitle;
    private String content;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
