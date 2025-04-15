package com.comnord.bqsm.services;

import com.comnord.bqsm.entity.DocumentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comnord.bqsm.repository.DocumentDataRepository;

import java.util.List;

@Service
public class DocumentDataService {

    @Autowired
    private DocumentDataRepository repository;

    public void saveDocumentData(List<String> paragraphs) {
        for (String paragraph : paragraphs) {
            DocumentData data = new DocumentData();
            data.setContent(paragraph);
            repository.save(data);
        }
    }
}
