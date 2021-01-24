package com.example.sigo.process.management.controller.dto;

import lombok.Data;

@Data
public class ActivityDTO {

    private long id;
    private String name;
    private ActivityStatus status;
}
