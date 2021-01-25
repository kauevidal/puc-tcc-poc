package com.example.sigo.process.management.controller;

import com.example.sigo.process.management.controller.dto.RequirementDTO;
import com.example.sigo.process.management.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "requirement", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequirementController {

    @Autowired
    private RequirementService requirementService;

    @GetMapping
    public List<RequirementDTO> getAll(@RequestParam long processId) {
        return requirementService.findAllByProcess(processId);
    }

    @PostMapping
    public RequirementDTO save(@RequestBody RequirementDTO requirementDTO) {
        return requirementService.save(requirementDTO);
    }
}
