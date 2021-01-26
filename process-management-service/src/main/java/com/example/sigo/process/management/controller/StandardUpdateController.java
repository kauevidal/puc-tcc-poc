package com.example.sigo.process.management.controller;

import com.example.sigo.process.management.controller.dto.StandardUpdateDTO;
import com.example.sigo.process.management.service.StandardUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "standard-update", produces = MediaType.APPLICATION_JSON_VALUE)
public class StandardUpdateController {

    @Autowired
    private StandardUpdateService standardUpdateService;

    @GetMapping
    public List<StandardUpdateDTO> getAll() {
        return standardUpdateService.findAll();
    }
}
