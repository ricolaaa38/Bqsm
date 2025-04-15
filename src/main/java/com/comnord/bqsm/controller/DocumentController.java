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

@RestController
public class DocumentController {

    @Autowired
    private WordDocumentService wordDocumentService;

    @Autowired
    private DocumentDataService documentDataService;

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file")MultipartFile file) {
        try {
            List<String> paragraphs = wordDocumentService.readWordDocument((file.getInputStream()));
            documentDataService.saveDocumentData(paragraphs);
            return "Document uploaded and data saved successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload document.";
        }
    }

    @GetMapping("/test")
    public String testRoute() {
        return "Test route is working!";
    }

}
