package com.example.sigo.standard.controller;

import com.example.sigo.standard.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "standard", produces = MediaType.APPLICATION_JSON_VALUE)
public class StandardController {

    @Autowired
    private StandardService standardService;

    @GetMapping
    public List<StandardDTO> getAll() {
        return standardService.findAll();
    }

    @PutMapping
    public StandardDTO update(@RequestBody StandardDTO standardDTO) {
        return standardService.update(standardDTO);
    }
}
