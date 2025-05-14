package com.comnord.bqsm.controller;

import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.service.BreveServices;
import com.comnord.bqsm.service.WordDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

//    @GetMapping("/")
//    public ResponseEntity<Iterable<BreveEntity>> getAllBreves() {
//       return ResponseEntity.ok(breveServices.getAllBreves());
//    }

    @GetMapping("/filtered")
    public ResponseEntity<Page<BreveEntity>> getAllBreves(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String intervenant,
            @RequestParam(required = false) String contributeur,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        Page<BreveEntity> breves = breveServices.getAllBreves(page, size, zone, categorie, intervenant, contributeur, startDate, endDate);
        return ResponseEntity.ok(breves);
    }

    @GetMapping("/sorted")
    public ResponseEntity<Page<BreveEntity>> getAllBrevesSortedByDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(breveServices.getAllBrevesSortedByDate(page, size));
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
