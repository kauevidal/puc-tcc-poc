package com.example.sigo.standard.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StandardDTO {

    private long id;

    private String title;

    private String keywords;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate publicationDate;

    private boolean inUse;

    private String url;
}
