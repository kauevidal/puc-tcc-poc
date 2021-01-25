package com.example.sigo.process.management.service;

import com.example.sigo.process.management.controller.dto.RequirementDTO;
import com.example.sigo.process.management.repository.RequirementRepository;
import com.example.sigo.process.management.repository.entity.RequirementEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequirementService {

    @Autowired
    private RequirementRepository requirementRepository;

    public List<RequirementDTO> findAllByProcess(long processId) {

        return mapDTOs(requirementRepository.findByProcessId(processId));
    }

    public RequirementDTO save(RequirementDTO requirementDTO) {

        RequirementEntity entity = new RequirementEntity();
        BeanUtils.copyProperties(requirementDTO, entity);
        RequirementEntity persisted = requirementRepository.save(entity);
        BeanUtils.copyProperties(persisted, requirementDTO);
        return requirementDTO;
    }

    private List<RequirementDTO> mapDTOs(Iterable<RequirementEntity> entities) {

        List<RequirementDTO> requirementDTOS = new ArrayList<>();

        entities.forEach(entity -> {
            RequirementDTO dto = new RequirementDTO();
            BeanUtils.copyProperties(entity, dto);
            requirementDTOS.add(dto);
        });

        return requirementDTOS;
    }
}
