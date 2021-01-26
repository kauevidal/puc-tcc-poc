package com.example.sigo.process.management.repository;

import com.example.sigo.process.management.repository.entity.StandardUpdateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardUpdateRepository extends CrudRepository<StandardUpdateEntity, Long> {

}
