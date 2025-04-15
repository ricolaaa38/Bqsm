package com.comnord.bqsm.services;

import com.comnord.bqsm.entity.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comnord.bqsm.repository.DocumentDataRepository;

import java.util.List;

@Service
public class DocumentDataService {

    @Autowired
    private DocumentDataRepository repository;

    public void saveSections(List<Section> sections) {
        repository.saveAll(sections);
    }
}
