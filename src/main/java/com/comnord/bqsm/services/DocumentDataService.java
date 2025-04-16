package com.comnord.bqsm.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comnord.bqsm.entity.DocumentEntity;
import com.comnord.bqsm.repository.DocumentDataRepository;

import java.util.List;
import java.util.Map;

@Service
public class DocumentDataService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentDataService.class);

    @Autowired
    private DocumentDataRepository documentDataRepository;

    public void saveDocumentData(List<Map<String, String>> extractedDataList) {
        for (Map<String, String> data : extractedDataList) {
            logger.debug("Data to save: {}", data);
            if (data.containsKey("Titre") && data.containsKey("Zone_lieu") &&
                    data.containsKey("Compte-rendu") && data.containsKey("Commentaire")) {
                DocumentEntity documentSection = new DocumentEntity();
                documentSection.setTitle(data.get("Titre"));
                documentSection.setZone(data.get("Zone_lieu"));
                documentSection.setReport(data.get("Compte-rendu"));
                documentSection.setComment(data.get("Commentaire"));
                documentDataRepository.save(documentSection);
                logger.info("Document saved: {}", documentSection);
            } else {
                logger.error("Missing data for document: {}", data);
                throw new IllegalArgumentException("Données manquantes pour sauvegarder le document.");
            }
        }
    }
}

