package com.example.sigo.process.management.repository;

import com.example.sigo.process.management.repository.entity.RequirementEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequirementRepository extends CrudRepository<RequirementEntity, Long> {

    public List<RequirementEntity> findByProcessId(long processId);
}
