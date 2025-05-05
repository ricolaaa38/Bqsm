package com.comnord.bqsm.controller;

import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.IntervenantEntity;
import com.comnord.bqsm.service.BreveServices;
import com.comnord.bqsm.service.IntervenantServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intervenants")
public class IntervenantControllers {

    @Autowired
    private IntervenantServices intervenantService;

    @Autowired
    private BreveServices breveServices;

    @GetMapping("/")
    public ResponseEntity<List<IntervenantEntity>> getIntervenantByBreveId(@RequestParam int breveId) {
        BreveEntity breve = breveServices.getBreveById(breveId);
        List<IntervenantEntity> intervenants = intervenantService.getAllIntervenantsByBreveId(breve);
        return ResponseEntity.ok(intervenants);
    }

    @PostMapping("/create")
    public ResponseEntity<IntervenantEntity> createintervenant(@RequestBody IntervenantEntity intervenant) {
        IntervenantEntity addIntervenant = intervenantService.saveIntervenant(intervenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(addIntervenant);
    }
}
