package com.comnord.bqsm.controller;

import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.service.BreveServices;
import com.comnord.bqsm.service.WordDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/breves")
public class BreveControllers {

    @Autowired
    private BreveServices breveServices;

    @Autowired
    private WordDocumentService wordDocumentService;

    @GetMapping("/")
    public ResponseEntity<Iterable<BreveEntity>> getAllBreves() {
       return ResponseEntity.ok(breveServices.getAllBreves());
    }

    @PostMapping("/upload")
    public String uploadBreves(@RequestParam("file") MultipartFile file) {
        try {
            List<Map<String, String>> extractedDataList = wordDocumentService.processDocument(file);
            breveServices.saveBrevesFromDocument(extractedDataList);
            return "Breves récupérés avec Succés!";
        } catch (Exception e) {
            return "Error uploading breves: " + e.getMessage();
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<BreveEntity> updateBreve(@RequestParam int id, @RequestBody Map<String, Object> updates) {
        try {
            BreveEntity updatedBreve = breveServices.updateBreve(id, updates);
            return new ResponseEntity<>(updatedBreve, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBreve(@RequestParam int id) {
        breveServices.deleteBreve(id);
        return new ResponseEntity<>("Breve deleted successfully", HttpStatus.OK);
    }
}
