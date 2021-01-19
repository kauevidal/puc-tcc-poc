package com.example.sigo.standard.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "sigo_standards")
public class StandardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String keywords;

    private LocalDate publicationDate;

    private boolean inUse;

    private String url;
}
