package com.ets.filesystem.web.controller;

import com.ets.filesystem.entity.*;
import com.ets.filesystem.service.*;
import com.ets.filesystem.web.model.*;
import com.ets.filesystem.web.model.File;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;


@RestController
@RequestMapping("api/v1/document")
@CrossOrigin("/**")
@SecurityRequirement(name = "etsdocapi")
public class DocumentController {

    @Autowired
    private FileStorageService storageService;

    public DocumentController(FileStorageService storageService) {
        this.storageService = storageService;
    }


    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam("dosyaAdi") String dosyaAdi,
            @RequestPart(required = true,name = "file") MultipartFile file) throws IOException {
        try {
            storageService.saveFile(file,dosyaAdi);
            return ResponseEntity.status(HttpStatus.CREATED).body("Upload File Succesfully  : " + file.getOriginalFilename());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    @GetMapping("/files")
    public ResponseEntity<List<File>> getListFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(storageService.getAllFiles());
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<HttpStatus> deleteFileById(@PathVariable Long id){

        boolean result = storageService.deleteFileById(id);

        if(result){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/files/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFile(
            @RequestParam("id") Long id,
            @RequestPart(required = true,name = "file") MultipartFile file){
        File updatedFile =  storageService.updateFile(file,id);
        if(updatedFile!=null){
            return new ResponseEntity<>(updatedFile,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<FileDB> getFile(@PathVariable String filename){
        ;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""  + "\"").body(storageService.loadFile(filename));
    }
}
