package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.DossierArborescenceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierArborescenceRepository extends CrudRepository<DossierArborescenceEntity, Integer> {
}
