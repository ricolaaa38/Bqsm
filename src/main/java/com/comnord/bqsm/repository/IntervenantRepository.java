package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.IntervenantEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervenantRepository extends CrudRepository<IntervenantEntity, Integer> {
}
