package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.BreveEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreveRepository extends CrudRepository<BreveEntity, Integer> {
}
