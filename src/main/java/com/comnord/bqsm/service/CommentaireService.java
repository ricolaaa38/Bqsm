package com.comnord.bqsm.service;

import com.comnord.bqsm.exception.CommentairesNotFoundException;
import com.comnord.bqsm.exception.ServiceException;
import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.CommentaireEntity;
import com.comnord.bqsm.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private BreveServices breveServices;

    public List<CommentaireEntity> getAllCommentairesByBreveId(BreveEntity breveId) {
     try {
         List<CommentaireEntity> commentaires = commentaireRepository.findAllCommentairesByBreveId(breveId);
         if (commentaires.isEmpty()) {
             throw new CommentairesNotFoundException("Aucun commentaire trouvé pour la brève avec l'ID : " + breveId.getId());
         }
         return commentaires;
     } catch (CommentairesNotFoundException e) {
         throw e;
     } catch (Exception e) {
         throw new ServiceException("Failed to retrieve commentaires", e);
     }
    }

    public CommentaireEntity saveCommentaire(CommentaireEntity commentaire) {
        try {
            return commentaireRepository.save(commentaire);
        } catch (Exception e) {
            throw new ServiceException("Failed to save commentaire", e);
        }
    }
}
