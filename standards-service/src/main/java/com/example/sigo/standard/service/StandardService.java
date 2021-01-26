package com.example.sigo.standard.service;

import com.example.sigo.standard.client.SNSClient;
import com.example.sigo.standard.controller.StandardDTO;
import com.example.sigo.standard.model.StandardEntity;
import com.example.sigo.standard.repository.StandardRepository;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StandardService {

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private SNSClient snsClient;

    public List<StandardDTO> findAll() {

        Iterable<StandardEntity> standardEntities = standardRepository.findAll();
        return mapDTOs(standardEntities);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public StandardDTO update(StandardDTO standardDTO) {

        List<StandardEntity> versions = standardRepository.findByTitle(standardDTO.getTitle());

        if (standardDTO.isInUse()) {
            versions.forEach(version -> version.setInUse(version.getId() == standardDTO.getId()));
            standardRepository.saveAll(versions);
            sendSNSMessage(standardDTO);
            return standardDTO;
        } else {
            sendSNSMessage(standardDTO);
            return save(standardDTO);
        }
    }

    private void sendSNSMessage(StandardDTO standardDTO) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("standardId", standardDTO.getId());
        jsonObject.put("title", standardDTO.getTitle());
        jsonObject.put("publicationDate", standardDTO.getPublicationDate());
        jsonObject.put("updateDate", LocalDate.now());
        jsonObject.put("inUse", standardDTO.isInUse());

        snsClient.publish(jsonObject.toString());
    }

    public StandardDTO save(StandardDTO standardDTO) {

        StandardEntity standardEntity = standardRepository.findById(standardDTO.getId()).orElseThrow();
        standardEntity.setInUse(standardDTO.isInUse());

        standardRepository.save(standardEntity);
        BeanUtils.copyProperties(standardEntity, standardDTO);
        return standardDTO;
    }

    private List<StandardDTO> mapDTOs(Iterable<StandardEntity> standardEntities) {

        List<StandardDTO> standardDTOS = new ArrayList<>();

        standardEntities.forEach(standardEntity -> {
            StandardDTO standardDTO = new StandardDTO();
            BeanUtils.copyProperties(standardEntity, standardDTO);
            standardDTOS.add(standardDTO);
        });

        return standardDTOS;
    }
}
