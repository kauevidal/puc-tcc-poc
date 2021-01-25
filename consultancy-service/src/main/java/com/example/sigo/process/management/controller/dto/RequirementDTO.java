package com.example.sigo.process.management.controller.dto;

import com.example.sigo.process.management.repository.entity.RequirementStatus;
import lombok.Data;

@Data
public class RequirementDTO {

    private long id;

    private String title;

    private String description;

    private RequirementStatus status;

    private long standardId;

    private long processId;
}
