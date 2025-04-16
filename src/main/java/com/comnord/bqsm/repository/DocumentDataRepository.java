package com.comnord.bqsm.repository;

import com.comnord.bqsm.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDataRepository extends JpaRepository<DocumentEntity, Long> {
}
