package com.example.sigo.mock.apis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StandardProviderController {

    private final ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(StandardProviderController.class);

    @Value("${api.key}")
    private String key;

    @GetMapping(value = "standard-provider")
    public ResponseEntity<String> getAllStandard(@RequestHeader("x-api-key") String apiKey, String filter) throws JsonProcessingException {

        logger.info("Getting standards");

        if (!apiKey.equals(key)) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);

        }
        return new ResponseEntity<>(mapper.readTree(standards).toString(), HttpStatus.OK);

    }

    private final String standards = "[" +
            "{\"id\":1,\"tile\":\"Norma 1\",\"key_words\":\"tecido, cor\",\"publication_date\":\"2020-06-05 10:00:00\"}," +
            "{\"id\":2,\"tile\":\"Norma 2\",\"key_words\":\"material, composição\",\"publication_date\":\"2021-02-05 10:00:00\"}" +
            "]";
}
