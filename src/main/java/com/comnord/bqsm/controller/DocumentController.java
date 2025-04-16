package com.comnord.bqsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.comnord.bqsm.services.DocumentDataService;
import com.comnord.bqsm.services.WordDocumentService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class DocumentController {

    @Autowired
    private WordDocumentService wordDocumentService;

    @Autowired
    private DocumentDataService documentDataService;

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            List<Map<String, String>> extractedDataList = wordDocumentService.processDocument(file);
            documentDataService.saveDocumentData(extractedDataList);
            return "Document traité avec succès!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors du traitement du document.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Test route is working!";
    }

}
