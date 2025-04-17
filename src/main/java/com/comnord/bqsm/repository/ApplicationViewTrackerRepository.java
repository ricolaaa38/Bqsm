package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.ApplicationViewTrackerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationViewTrackerRepository extends CrudRepository<ApplicationViewTrackerEntity, Integer> {
}
