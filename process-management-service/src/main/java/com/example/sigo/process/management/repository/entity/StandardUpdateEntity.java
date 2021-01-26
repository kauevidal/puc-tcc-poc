package com.example.sigo.process.management.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "sigo_standard_update")
public class StandardUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long standardId;

    private String title;

    private LocalDate publicationDate;

    private LocalDate updateDate;

    private boolean inUse;
}
