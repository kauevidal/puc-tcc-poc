package com.example.sigo.process.management.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderDTO {

    private long id;

    private String buyer;

    private int quantity;

    @JsonProperty("due_date")
    private String dueDate;
}
