package com.example.sigo.mock.apis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessMockController {

    private final ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(ProcessMockController.class);

    @Value("${mock.api.key}")
    private String key;

    @GetMapping(value = "process-mock")
    public ResponseEntity<String> getAllProcesses(@RequestHeader("x-api-key") String apiKey) throws JsonProcessingException {

        logger.info("Getting processes");

        if (!apiKey.equals(key)) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(mapper.readTree(processes).toString(), HttpStatus.OK);
    }

    @GetMapping(value = "activity-mock")
    public ResponseEntity<String> getActivities(@RequestHeader("x-api-key") String apiKey, @RequestParam Long processId) throws JsonProcessingException {

        logger.info("Getting activity detail");

        if (!apiKey.equals(key)) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        if (processId == 1) {
            return new ResponseEntity<>(mapper.readTree(activities).toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(mapper.readTree(activities2).toString(), HttpStatus.OK);
        }
    }

    private final String processes = "[" +
            "{\"id\":1,\"name\":\"Processo 1\",\"status\":\"operational\",\"material_status\":\"100\",\"capacity\":\"300\",\"order\":[{\"id\":1,\"buyer\":\"comprador 1\",\"quantity\":\"1500\",\"due_date\":\"02-02-2021\"}]}," +
            "{\"id\":2,\"name\":\"Processo 2\",\"status\":\"problem\",\"material_status\":\"0\",\"capacity\":\"100\",\"order\":[{\"id\":2,\"buyer\":\"comprador 2\",\"quantity\":\"20000\",\"due_date\":\"05-06-2021\"}]}" +
            "]";

    private final String activities = "[" +
            "{\"id\":1,\"name\":\"Atividade 1\",\"description\":\"Compra de insumos\",\"status\":\"OK\"}," +
            "{\"id\":2,\"name\":\"Atividade 2\",\"description\":\"Descarte resíduos industriais\",\"status\":\"OK\"}" +
            "]";

    private final String activities2 = "[" +
            "{\"id\":3,\"name\":\"Atividade 3\",\"description\":\"Manutenção de máquinas\",\"status\":\"PROBLEM\"}," +
            "{\"id\":4,\"name\":\"Atividade 4\",\"description\":\"Verificação de qualidade\",\"status\":\"OK\"}"
            + "]";
}
