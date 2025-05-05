package com.comnord.bqsm.controller;

import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.BreveViewTrackerEntity;
import com.comnord.bqsm.service.BreveServices;
import com.comnord.bqsm.service.BreveViewTrackerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/breveviewtracker")
public class BreveViewTrackerControllers {

    @Autowired
    private BreveViewTrackerServices breveViewTrackerServices;

    @Autowired
    private BreveServices breveServices;

    @GetMapping("/")
    public ResponseEntity<BreveViewTrackerEntity> getBreveViewTrackerByBreveId(@RequestParam int breveId) {
        BreveEntity breve = breveServices.getBreveById(breveId);
        BreveViewTrackerEntity breveViewTracker = breveViewTrackerServices.getBreveViewTrackerByBreveId(breve);
        return ResponseEntity.ok(breveViewTracker);
    }
}
