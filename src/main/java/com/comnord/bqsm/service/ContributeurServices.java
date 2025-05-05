package com.comnord.bqsm.service;

import com.comnord.bqsm.exception.ContributeursNotFoundException;
import com.comnord.bqsm.exception.ServiceException;
import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.ContributeurEntity;
import com.comnord.bqsm.repository.ContributeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContributeurServices {

    @Autowired
    private ContributeurRepository contributeurRepository;

    @Autowired
    private BreveServices breveServices;

    public List<ContributeurEntity> getAllContributeursByBreveId(BreveEntity breveId) {
         try {
             List<ContributeurEntity> contributeurs = contributeurRepository.findAllContributeursByBreveId(breveId);
             if (contributeurs.isEmpty()) {
                 throw new ContributeursNotFoundException("Aucun contributeur trouvé pour la brève avec l'id : " + breveId.getId());
             }
             return contributeurs;
         } catch (ContributeursNotFoundException e) {
             throw e;
         } catch (Exception e) {
             throw new ServiceException("Failed to retrieve contributeurs", e);
         }
    }

    public ContributeurEntity saveContributeur(ContributeurEntity contributeur) {
        try {
            return contributeurRepository.save(contributeur);
        } catch (Exception e) {
            throw new ServiceException("Failed to save contributeur", e);
        }
    }
}
