package com.comnord.bqsm.services;

import com.comnord.bqsm.entity.Section;
import com.comnord.bqsm.entity.SubSection;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordDocumentService {

    private static final Logger logger = LoggerFactory.getLogger(WordDocumentService.class);

    /**
     * Lit un document Word et extrait les sections et sous-sections.
     *
     * @param inputStream le flux d'entrée du document Word
     * @return une liste de sections extraites du document
     * @throws IOException si une erreur survient lors de la lecture du document
     */
    public List<Section> readWordDocument(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        List<Section> sections = new ArrayList<>();
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            Section currentSection = null;
            SubSection currentSubSection = null;

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText().trim();
                logger.debug("Processing paragraph: {}", text);

                if (text.startsWith("Titre :")) { // Titre de la section
                    if (currentSection != null) {
                        sections.add(currentSection); // Ajoute la section précédente
                    }
                    currentSection = new Section();
                    currentSection.setTitle(getContentAfterColon(text)); // Extrait le titre après "Titre :"
                    currentSection.setSubSections(new ArrayList<>());
                    logger.debug("Found Title: {}", currentSection.getTitle());
                } else if (text.startsWith("Zone :")) { // Sous-titre
                    if (currentSection == null) {
                        logger.error("Zone found without a preceding Title");
                        throw new IllegalStateException("Zone found without a preceding Title");
                    }
                    currentSubSection = new SubSection();
                    currentSubSection.setSubtitle(getContentAfterColon(text)); // Extrait la zone après "Zone :"
                    currentSubSection.setContent("");
                    currentSubSection.setSection(currentSection);
                    currentSection.getSubSections().add(currentSubSection);
                    logger.debug("Found Zone: {}", currentSubSection.getSubtitle());
                } else if (text.startsWith("Compte-rendu :")) { // Début du compte-rendu
                    if (currentSubSection == null) {
                        logger.error("Compte-rendu found without a preceding Zone");
                        throw new IllegalStateException("Compte-rendu found without a preceding Zone");
                    }
                    currentSubSection.setContent(currentSubSection.getContent() + getContentAfterColon(text) + "\n");
                    logger.debug("Found Compte-rendu: {}", getContentAfterColon(text));
                } else if (text.startsWith("Commentaire :")) { // Commentaire
                    if (currentSection == null) {
                        logger.error("Commentaire found without a preceding Title");
                        throw new IllegalStateException("Commentaire found without a preceding Title");
                    }
                    currentSection.setComment(getContentAfterColon(text)); // Extrait le commentaire après "Commentaire :"
                    logger.debug("Found Commentaire: {}", getContentAfterColon(text));
                } else if (currentSubSection != null) { // Texte additionnel pour le compte-rendu
                    currentSubSection.setContent(currentSubSection.getContent() + text + "\n");
                    logger.debug("Adding additional content to Compte-rendu: {}", text);
                }
            }

            // Ajoute la dernière section
            if (currentSection != null) {
                sections.add(currentSection);
            }
        }
        return sections;
    }

    // Méthode pour extraire le contenu après un ":"
    private String getContentAfterColon(String text) {
        int colonIndex = text.indexOf(':');
        if (colonIndex == -1) {
            return text; // Retourne le texte complet si ":" n'est pas trouvé
        }
        return text.substring(colonIndex + 1).trim();
    }
}

