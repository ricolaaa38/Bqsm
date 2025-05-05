package com.comnord.bqsm.controller;

import com.comnord.bqsm.model.BreveEntity;
import com.comnord.bqsm.model.PictureEntity;
import com.comnord.bqsm.service.BreveServices;
import com.comnord.bqsm.service.PictureServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pictures")
public class PictureControllers {

    @Autowired
    private PictureServices pictureServices;

    @Autowired
    private BreveServices breveServices;

    @GetMapping("/")
    public ResponseEntity<List<PictureEntity>> getPictureByBreveId(@RequestParam int breveId) {
        BreveEntity breve = breveServices.getBreveById(breveId);
        List<PictureEntity> pictures = pictureServices.getAllPictureByBreveId(breve);
        return ResponseEntity.ok(pictures);
    }

    @PostMapping("/create")
    public ResponseEntity<PictureEntity> createPicture(@RequestParam String name, @RequestParam int breveId, @RequestParam MultipartFile imageFile) {
        try {
            BreveEntity breve = breveServices.getBreveById(breveId);
            PictureEntity picture = new PictureEntity();
            picture.setName(name);
            picture.setBreveId(breve);
            picture.setImage(imageFile.getBytes());
            PictureEntity addPicture = pictureServices.savePicture(picture);
            return ResponseEntity.status(HttpStatus.CREATED).body(addPicture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
