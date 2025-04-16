package com.comnord.bqsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "breves")
@Data
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String zone;
    private String report;
    private String comment;

}
