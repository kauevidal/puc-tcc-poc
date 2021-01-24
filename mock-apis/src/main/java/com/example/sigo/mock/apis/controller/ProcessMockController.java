package com.example.sigo.mock.apis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessMockController {

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "process-mock")
    public String getAllProcesses() throws JsonProcessingException {
        return mapper.readTree(processes).toString();
    }

    @GetMapping(value = "activity-mock")
    public String getActivities(@RequestParam Long processId) throws JsonProcessingException {
        if (processId == 1) {
            return mapper.readTree(activities).toString();
        } else {
            return mapper.readTree(activities2).toString();
        }
    }

    private final String processes = "[" +
            "{\"id\":1,\"name\":\"processo 1\",\"status\":\"operational\",\"material_status\":\"100\",\"capacity\":\"300\",\"order\":[{\"id\":1,\"buyer\":\"comprador 1\",\"quantity\":\"1500\",\"due_date\":\"02-02-2021\"}]}," +
            "{\"id\":2,\"name\":\"processo 2\",\"status\":\"problem\",\"material_status\":\"0\",\"capacity\":\"100\",\"order\":[{\"id\":2,\"buyer\":\"comprador 2\",\"quantity\":\"20000\",\"due_date\":\"05-06-2021\"}]}" +
            "]";

    private final String activities = "[" +
            "{\"id\":1,\"name\":\"atividade 1\",\"status\":\"OK\"}," +
            "{\"id\":2,\"name\":\"atividade 2\",\"status\":\"OK\"}" +
            "]";

    private final String activities2 = "[" +
            "{\"id\":3,\"name\":\"atividade 3\",\"status\":\"PROBLEM\"}," +
            "{\"id\":4,\"name\":\"atividade 4\",\"status\":\"OK\"}"
            + "]";
}
