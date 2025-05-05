package com.comnord.bqsm.controller;

import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.ContributeurEntity;
import com.comnord.bqsm.service.BreveServices;
import com.comnord.bqsm.service.ContributeurServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contributeurs")
public class ContributeurControllers {

    @Autowired
    private ContributeurServices contributeurService;

    @Autowired
    private BreveServices breveServices;

    @GetMapping("/")
    public ResponseEntity<List<ContributeurEntity>> getContributeursByBreveId(@RequestParam int breveId) {
        BreveEntity breve = breveServices.getBreveById(breveId);
        List<ContributeurEntity> contributeurs = contributeurService.getAllContributeursByBreveId(breve);
        return ResponseEntity.ok(contributeurs);
    }

    @PostMapping("/create")
    public ResponseEntity<ContributeurEntity> createContributeur(@RequestBody ContributeurEntity contributeur) {
        ContributeurEntity addContributeur = contributeurService.saveContributeur(contributeur);
        return ResponseEntity.status(HttpStatus.CREATED).body(addContributeur);
    }

}
