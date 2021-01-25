package com.example.sigo.process.management.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProcessDTO {

    private long id;

    private String name;

    private String status;

    @JsonProperty("material_status")
    private int materialStatus;

    private int capacity;

    private List<OrderDTO> order;
}
