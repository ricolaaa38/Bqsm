package com.comnord.bqsm.controller;

import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.CommentaireEntity;
import com.comnord.bqsm.service.BreveServices;
import com.comnord.bqsm.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireControllers {

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private BreveServices breveServices;

    @GetMapping("/")
    public ResponseEntity<List<CommentaireEntity>> getCommentairesByBreveId(@RequestParam int breveId) {
        BreveEntity breve = breveServices.getBreveById(breveId);
        List<CommentaireEntity> commentaires = commentaireService.getAllCommentairesByBreveId(breve);
        return ResponseEntity.ok(commentaires);
    }

    @PostMapping("/create")
    public ResponseEntity<CommentaireEntity> createCommentaire(@RequestBody CommentaireEntity commentaire) {
        CommentaireEntity addCommentaire = commentaireService.saveCommentaire(commentaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(addCommentaire);
    }

}
