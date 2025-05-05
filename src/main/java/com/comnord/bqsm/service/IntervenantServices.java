package com.comnord.bqsm.service;

import com.comnord.bqsm.exception.IntervenantsNotFoundException;
import com.comnord.bqsm.exception.ServiceException;
import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.IntervenantEntity;
import com.comnord.bqsm.repository.IntervenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntervenantServices {

    @Autowired
    private IntervenantRepository intervenantRepository;

    @Autowired
    private BreveServices breveServices;

    public List<IntervenantEntity> getAllIntervenantsByBreveId(BreveEntity breveId) {
       try {
           List<IntervenantEntity> intervenants = intervenantRepository.findAllIntervenantsByBreveId(breveId);
           if (intervenants.isEmpty()) {
               throw new IntervenantsNotFoundException("Aucun intervenant trouvé pour la brève avec l'Id : " + breveId.getId());
           }
           return intervenants;
       } catch (IntervenantsNotFoundException e) {
           throw e;
       } catch (Exception e) {
           throw new ServiceException("Failed to retrieve intervenants", e);
       }
    }

    public IntervenantEntity saveIntervenant(IntervenantEntity intervenant) {
        try {
            return intervenantRepository.save(intervenant);
        } catch (Exception e) {
            throw new ServiceException("Failed to save intervenant", e);
        }
    }

}
