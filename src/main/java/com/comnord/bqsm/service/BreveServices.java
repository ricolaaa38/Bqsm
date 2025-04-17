package com.comnord.bqsm.service;

import com.comnord.bqsm.exception.BrevesNotFoundException;
import com.comnord.bqsm.exception.InvalidFileException;
import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.repository.BreveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BreveServices {

    @Autowired
    private BreveRepository breveRepository;

    @Autowired
    private WordDocumentService wordDocumentService;

    public Iterable<BreveEntity> getAllBreves() {
       Iterable<BreveEntity> breves = breveRepository.findAll();
       if (!breves.iterator().hasNext()) {
           throw new BrevesNotFoundException("No breves were found in the database.");
       }
       return breves;
    }

}
