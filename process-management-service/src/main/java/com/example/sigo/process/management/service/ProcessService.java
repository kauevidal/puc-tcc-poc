package com.example.sigo.process.management.service;

import com.example.sigo.process.management.client.MockAPIClient;
import com.example.sigo.process.management.controller.dto.ProcessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessService {

    @Autowired
    private MockAPIClient client;

    public List<ProcessDTO> findAll() {
        return client.getAllProcess();
    }
}
