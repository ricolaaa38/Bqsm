package com.comnord.bqsm.controller;

import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.service.BreveServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/breves")
public class BreveControllers {

    @Autowired
    private BreveServices breveServices;

    @GetMapping("/")
    public ResponseEntity<Iterable<BreveEntity>> getAllBreves() {
       return ResponseEntity.ok(breveServices.getAllBreves());
    }
    
}
