package com.example.sigo.standard.repository;

import com.example.sigo.standard.model.StandardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardRepository extends CrudRepository<StandardEntity, Long> {

    public List<StandardEntity> findByTitle(String title);
}
