package com.example.sigo.process.management.client;

import com.example.sigo.process.management.controller.dto.StandardUpdateDTO;
import com.example.sigo.process.management.repository.StandardUpdateRepository;
import com.example.sigo.process.management.repository.entity.StandardUpdateEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SQSListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(SQSListener.class);

    @Autowired
    private StandardUpdateRepository standardUpdateRepository;

    @SqsListener("${process.management.queue.name}")
    public void listenMessages(String standardMessage) throws JsonProcessingException {

        StandardUpdateEntity entity = mapEntity(standardMessage);
        standardUpdateRepository.save(entity);
    }

    private StandardUpdateEntity mapEntity(String json) throws JsonProcessingException {

        objectMapper.registerModule(new JavaTimeModule());

        logger.info(String.format("Mensagem recebida: %s", json));

        StandardUpdateDTO standardUpdateDTO = objectMapper.readValue(json, StandardUpdateDTO.class);
        StandardUpdateEntity entity = new StandardUpdateEntity();
        BeanUtils.copyProperties(standardUpdateDTO, entity);
        return entity;
    }
}
