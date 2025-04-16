package com.comnord.bqsm.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class WordDocumentService {

    private static final Logger logger = LoggerFactory.getLogger(WordDocumentService.class);
    private static final String BREVES_MARKER = "Breves";
    private static final String TITRE_MARKER = "Titre";
    private static final String ZONE_LIEU_MARKER = "Zone_lieu";
    private static final String COMPTE_RENDU_MARKER = "Compte-rendu";
    private static final String COMMENTAIRE_MARKER = "Commentaire";

    public List<Map<String, String>> processDocument(MultipartFile file) throws IOException {
        List<Map<String, String>> extractedDataList = new ArrayList<>();
        try (InputStream is = file.getInputStream()) {
            XWPFDocument document = new XWPFDocument(is);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            Map<String, String> currentData = new HashMap<>();
            String currentKey = null;
            StringBuilder currentValue = new StringBuilder();

            for (XWPFParagraph paragraph : paragraphs) {
                String text = paragraph.getText().trim();
                logger.debug("Paragraph text: '{}'", text);

                if (text.equalsIgnoreCase(BREVES_MARKER)) {
                    if (currentKey != null) {
                        currentData.put(currentKey, currentValue.toString().trim());
                        logger.debug("Saved data for key: {}", currentKey);
                    }
                    if (!currentData.isEmpty()) {
                        extractedDataList.add(currentData);
                        currentData = new HashMap<>();
                    }
                    currentKey = null;
                    currentValue = new StringBuilder();
                } else if (startsWithAnyMarker(text)) {
                    saveCurrentData(currentData, currentKey, currentValue);
                    currentKey = getKeyForMarker(text);
                    currentValue = new StringBuilder(getTextAfterMarker(text, currentKey));
                } else if (!text.isEmpty()) {
                    appendToCurrentValue(currentKey, currentValue, text);
                }
            }
            saveCurrentData(currentData, currentKey, currentValue);
            if (!currentData.isEmpty()) {
                extractedDataList.add(currentData);
            }
        }
        logger.info("Données extraites : {}", extractedDataList);
        return extractedDataList;
    }

    private void saveCurrentData(Map<String, String> currentData, String currentKey, StringBuilder currentValue) {
        if (currentKey != null) {
            currentData.put(currentKey, currentValue.toString().trim());
            logger.debug("Saved data for key: {}", currentKey);
        }
    }

    private boolean startsWithAnyMarker(String text) {
        return text.startsWith(TITRE_MARKER) ||
                text.startsWith(ZONE_LIEU_MARKER) ||
                text.startsWith(COMPTE_RENDU_MARKER) ||
                text.startsWith(COMMENTAIRE_MARKER);
    }

    private String getKeyForMarker(String text) {
        if (text.startsWith(TITRE_MARKER)) return "Titre";
        if (text.startsWith(ZONE_LIEU_MARKER)) return "Zone_lieu";
        if (text.startsWith(COMPTE_RENDU_MARKER)) return "Compte-rendu";
        if (text.startsWith(COMMENTAIRE_MARKER)) return "Commentaire";
        return null;
    }

    private String getTextAfterMarker(String text, String key) {
        switch (key) {
            case "Titre": return text.substring(TITRE_MARKER.length()).trim();
            case "Zone_lieu": return text.substring(ZONE_LIEU_MARKER.length()).trim();
            case "Compte-rendu": return text.substring(COMPTE_RENDU_MARKER.length()).trim();
            case "Commentaire": return text.substring(COMMENTAIRE_MARKER.length()).trim();
            default: return "";
        }
    }

    private void appendToCurrentValue(String currentKey, StringBuilder currentValue, String text) {
        if (currentKey != null) {
            currentValue.append(" ").append(text);
        }
    }
}