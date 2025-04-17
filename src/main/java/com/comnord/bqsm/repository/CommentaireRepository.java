package com.comnord.bqsm.repository;

import com.comnord.bqsm.model.CommentaireEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaireRepository extends CrudRepository<CommentaireEntity, Integer> {
}
