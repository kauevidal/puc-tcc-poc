package com.example.sigo.process.management.service;

import com.example.sigo.process.management.controller.dto.StandardUpdateDTO;
import com.example.sigo.process.management.repository.StandardUpdateRepository;
import com.example.sigo.process.management.repository.entity.StandardUpdateEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StandardUpdateService {

    @Autowired
    private StandardUpdateRepository standardUpdateRepository;

    public List<StandardUpdateDTO> findAll() {

        return mapDTOs(standardUpdateRepository.findAll());
    }

    private List<StandardUpdateDTO> mapDTOs(Iterable<StandardUpdateEntity> standardEntities) {

        List<StandardUpdateDTO> standardDTOS = new ArrayList<>();

        standardEntities.forEach(standardEntity -> {
            StandardUpdateDTO standardDTO = new StandardUpdateDTO();
            BeanUtils.copyProperties(standardEntity, standardDTO);
            standardDTOS.add(standardDTO);
        });

        return standardDTOS;
    }
}
