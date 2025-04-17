package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.FichierArborescenceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichierArborescenceRepository extends CrudRepository<FichierArborescenceEntity, Integer> {
}
