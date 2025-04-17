package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.BreveViewTrackerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreveViewTrackerRepository extends CrudRepository<BreveViewTrackerEntity, Integer> {
}
