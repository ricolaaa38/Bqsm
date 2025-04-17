package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.ContributeurEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributeurRepository extends CrudRepository<ContributeurEntity, Integer> {
}
