package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.LinkEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<LinkEntity, Integer> {
}
