package com.comnord.bqsm.controller;

import com.comnord.bqsm.exception.DossierArborescenceNotFoundException;
import com.comnord.bqsm.model.DossierArborescenceEntity;
import com.comnord.bqsm.model.FichierArborescenceEntity;
import com.comnord.bqsm.model.dto.FolderChildrenDTO;
import com.comnord.bqsm.repository.DossierArborescenceRepository;
import com.comnord.bqsm.repository.FichierArborescenceRepository;
import com.comnord.bqsm.service.DossierArborescenceServices;
import com.comnord.bqsm.service.FichierArborescenceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/arborescence")
public class ArborescenceControllers {

    @Autowired
    private DossierArborescenceServices dossierArborescenceServices;

    @Autowired
    private DossierArborescenceRepository dossierArborescenceRepository;

    @Autowired
    private FichierArborescenceServices fichierArborescenceServices;

    @Autowired
    private FichierArborescenceRepository fichierArborescenceRepository;

    @GetMapping("/dossiers")
    public ResponseEntity<Iterable<DossierArborescenceEntity>> getAllDossiers() {
        return ResponseEntity.ok(dossierArborescenceServices.getAllDossiers());
    }

    @GetMapping("/files")
    public ResponseEntity<Iterable<FichierArborescenceEntity>> getAllFiles() {
        return ResponseEntity.ok(fichierArborescenceServices.getAllFiles());
    }

    @GetMapping("/child")
    public ResponseEntity<?> getAllFoldersChildren(@RequestParam int parentId) {
        DossierArborescenceEntity parent = dossierArborescenceRepository.findById(parentId)
                .orElseThrow(() -> new DossierArborescenceNotFoundException("Parent folder not found"));

        List<DossierArborescenceEntity> childFolders = dossierArborescenceServices.getAllChildDossiersByParentId(parent);
        List<FichierArborescenceEntity> childFiles = fichierArborescenceServices.getAllFilesByParentId(parent);

        FolderChildrenDTO folderChildrenDTO = new FolderChildrenDTO();
        folderChildrenDTO.setFolders(childFolders);
        folderChildrenDTO.setFiles(childFiles);
        return ResponseEntity.ok(folderChildrenDTO);
    }

    @PostMapping("/create-folder")
    public ResponseEntity<?> createFolder(@RequestBody DossierArborescenceEntity dossier) {
        Optional<DossierArborescenceEntity> alreadyExistFolder = dossierArborescenceRepository.findDossierByParentIdAndName(dossier.getParentId(), dossier.getName());
        if (alreadyExistFolder.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le dossier '" + dossier.getName() + "' existe déjà à cette endroit.");
        } else {
            DossierArborescenceEntity addDossier = dossierArborescenceServices.saveDossier(dossier);
            return ResponseEntity.status(HttpStatus.CREATED).body(addDossier);
        }
    }

    @PostMapping("/update-folder")
    public ResponseEntity<?> updateFolder(@RequestBody DossierArborescenceEntity dossier) {
        Optional<DossierArborescenceEntity> existingFolder = dossierArborescenceRepository.findById(dossier.getId());
        Optional<DossierArborescenceEntity> alreadyExistEntry = dossierArborescenceRepository.findDossierByParentIdAndName(dossier.getParentId(), dossier.getName());
        if (alreadyExistEntry.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le dossier '" + dossier.getName() + "' existe déjà dans le dossier '" + dossierArborescenceRepository.findById(dossier.getParentId().getId()).get().getName() + "'.");
        }
        if (existingFolder.isPresent()) {
            DossierArborescenceEntity updatedDossier = dossierArborescenceServices.updateDossier(dossier, existingFolder.get());
            return ResponseEntity.status(HttpStatus.OK).body(updatedDossier);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dossier non trouvé avec l'ID: " + dossier.getId());
        }
    }

    @PostMapping("/create-file")
    public ResponseEntity<?> createFile(@RequestParam DossierArborescenceEntity parentId, @RequestParam String name, @RequestParam MultipartFile file) {
        try {
            Optional<FichierArborescenceEntity> alreadyExistFile = fichierArborescenceRepository.findFileByparentIdAndName(parentId, name);
            if (alreadyExistFile.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Un fichier '" + name + "' existe déjà à cette endroit.");
            }
            FichierArborescenceEntity fichier = new FichierArborescenceEntity();
            fichier.setParentId(parentId);
            fichier.setName(name);
            fichier.setFile(file.getBytes());
            FichierArborescenceEntity addFichier = fichierArborescenceServices.saveFile(fichier);
            return ResponseEntity.status(HttpStatus.CREATED).body(addFichier);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/update-file")
    public ResponseEntity<?> updateFile(@RequestBody FichierArborescenceEntity fichier) {
        Optional<FichierArborescenceEntity> existingFile = fichierArborescenceRepository.findById(fichier.getId());
        Optional<FichierArborescenceEntity> alreadyExistEntry = fichierArborescenceRepository.findFileByparentIdAndName(fichier.getParentId(), fichier.getName());
        if (alreadyExistEntry.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Il existe déjà un fichier nommé '" + fichier.getName() + "' dans le dossier '" + dossierArborescenceRepository.findById(fichier.getParentId().getId()).get().getName() + "'.");
        }
        if (existingFile.isPresent()) {
            FichierArborescenceEntity updatedFichier = fichierArborescenceServices.updateFile(fichier, existingFile.get());
            return ResponseEntity.status(HttpStatus.OK).body(updatedFichier);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fichier non trouvé avec l'ID: " + fichier.getId());
        }
    }

    @DeleteMapping("/delete-folder")
    public ResponseEntity<String> deleteFolder(@RequestParam int id) {
        dossierArborescenceServices.deleteDossierById(id);
        return new ResponseEntity<>("Dossier supprimé avec succès", HttpStatus.OK);
    }

    @DeleteMapping("/delete-file")
    public ResponseEntity<String> deleteFile(@RequestParam int id) {
        fichierArborescenceServices.deleteFileById(id);
        return new ResponseEntity<>("Fichier supprimé avec succès", HttpStatus.OK);
    }

}
