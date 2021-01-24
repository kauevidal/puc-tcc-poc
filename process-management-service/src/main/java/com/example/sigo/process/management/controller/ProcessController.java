package com.example.sigo.process.management.controller;

import com.example.sigo.process.management.service.ProcessService;
import com.example.sigo.process.management.controller.dto.ProcessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "process", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping
    public List<ProcessDTO> getAllProcesses() {
        return processService.findAll();
    }
}
