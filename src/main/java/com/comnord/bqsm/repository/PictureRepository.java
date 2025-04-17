package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.PictureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends CrudRepository<PictureEntity, Integer> {
}
