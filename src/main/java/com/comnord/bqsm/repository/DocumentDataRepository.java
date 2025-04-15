package com.comnord.bqsm.repository;

import com.comnord.bqsm.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDataRepository extends JpaRepository<Section, Long> {
}
