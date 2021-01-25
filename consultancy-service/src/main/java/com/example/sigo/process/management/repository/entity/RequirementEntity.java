package com.example.sigo.process.management.repository.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sigo_requirement")
public class RequirementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private RequirementStatus status;

    private long standardId;

    private long processId;
}
