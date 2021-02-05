package com.example.sigo.process.management.client;

import com.example.sigo.process.management.controller.dto.ActivityDTO;
import com.example.sigo.process.management.controller.dto.ProcessDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class MockAPIClient {

    private final Logger logger = LoggerFactory.getLogger(MockAPIClient.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${mock.api.url}")
    private String mockApiUrl;

    @Value("${mock.api.endpoint.process}")
    private String processEndpoint;

    @Value("${mock.api.endpoint.activity}")
    private String activityEndpoint;

    @Value("${mock.api.key}")
    private String key;

    public List<ProcessDTO> getAllProcess() {

        String urlRequest = UriComponentsBuilder.
                fromHttpUrl(mockApiUrl)
                .path(processEndpoint)
                .build()
                .toString();

        logger.info(String.format("URL request: %s", urlRequest));

        ResponseEntity<List<ProcessDTO>> response = restTemplate.exchange(urlRequest,
                HttpMethod.GET, buildRequest(), new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    public List<ActivityDTO> getActivityByProcess(long processId) {

        String urlRequest = UriComponentsBuilder.
                fromHttpUrl(mockApiUrl)
                .path(activityEndpoint)
                .queryParam("processId", processId)
                .build()
                .toString();

        logger.info(String.format("URL request: %s", urlRequest));

        ResponseEntity<List<ActivityDTO>> response = restTemplate.exchange(urlRequest,
                HttpMethod.GET, buildRequest(), new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    private HttpEntity<String> buildRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", key);
        return new HttpEntity<>(null, headers);
    }
}
