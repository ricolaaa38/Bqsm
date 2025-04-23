package com.comnord.bqsm.service;

import com.comnord.bqsm.exception.BrevesNotFoundException;
import com.comnord.bqsm.exception.ServiceException;
import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.repository.BreveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Service
public class BreveServices {

    private static final Logger logger = LoggerFactory.getLogger(BreveServices.class);

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

    public BreveEntity getBreveById(int id) {
        return breveRepository.findById(id)
                .orElseThrow(() -> new BrevesNotFoundException("Breve not found with ID: " + id));
    }

    public void saveBrevesFromDocument(List<Map<String, String>> extractedDataList) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Map<String, String> data : extractedDataList) {
            logger.debug("Processing data: {}", data);
            if (data.containsKey("BqsmNum") && data.containsKey("Date")
                    && data.containsKey("Titre") && data.containsKey("Categorie")
                    && data.containsKey("Zone_lieu") && data.containsKey("Pays") && data.containsKey("Latitude")
                    && data.containsKey("Longitude") && data.containsKey("Contenu")) {
                BreveEntity breve = new BreveEntity();
                breve.setBqsmNumb(data.get("BqsmNum"));
                try {
                    LocalDate date = LocalDate.parse(data.get("Date"), dateFormatter);
                    breve.setDate(date.atStartOfDay());
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Format de date invalide : " + data.get("Date"), e);
                }
                breve.setTitre(data.get("Titre"));
                breve.setCategorie(data.get("Categorie"));
                breve.setZone(data.get("Zone_lieu"));
                breve.setPays(data.get("Pays"));
                try {
                    breve.setLatitude(new BigDecimal(data.get("Latitude")));
                    breve.setLongitude(new BigDecimal(data.get("Longitude")));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Format de latitude ou longitude invalide : " + data.get("Latitude") + ", " + data.get("Longitude"), e);
                }
                breve.setContenu(data.get("Contenu"));
                breveRepository.save(breve);
                logger.info("Breves saved: {}", breve);
            } else {
                throw new ServiceException("Données manquantes pour sauvegarder le document");
            }
        }
    }

    public BreveEntity updateBreve(int id, Map<String, Object> updates) {
        BreveEntity existingBreve = breveRepository.findById(id)
                .orElseThrow(() -> new BrevesNotFoundException("Breve introuvable avec l'ID : " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "bqsmNumb":
                    existingBreve.setBqsmNumb((String) value);
                    break;
                case "date":
                    existingBreve.setDate(LocalDate.parse((String) value).atStartOfDay());
                    break;
                case "titre":
                    existingBreve.setTitre((String) value);
                    break;
                case "categorie":
                    existingBreve.setCategorie((String) value);
                    break;
                case "zone":
                    existingBreve.setZone((String) value);
                    break;
                case "pays":
                    existingBreve.setPays((String) value);
                    break;
                case "latitude":
                    existingBreve.setLatitude(new BigDecimal(value.toString()));
                    break;
                case "longitude":
                    existingBreve.setLongitude(new BigDecimal(value.toString()));
                    break;
                case "contenu":
                    existingBreve.setContenu((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Champ non valide : " + key);
            }
        });

        return breveRepository.save(existingBreve);
    }

    public void deleteBreve(int id) {
        if (!breveRepository.existsById(id)) {
            throw new BrevesNotFoundException("Breve introuvable avec l'ID : " + id);
        }
        try {
            breveRepository.deleteById(id);
            logger.info("Breve supprimée avec succès : ID {}", id);
        } catch (Exception e) {
            throw new ServiceException("Échec de suppression de la Breve ", e);
        }
    }

}
