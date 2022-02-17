package com.ets.filesystem.web.controller;

import com.ets.filesystem.service.*;
import com.ets.filesystem.web.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;

@RestController
@RequestMapping("api/v1/document")
public class DocumentController {

    @Autowired
    private FileStorageService storageService;

    public DocumentController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> getHello(){

        return ResponseEntity.ok().body("HELLO DOCUMENT PROJECT");
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        String message = "";
        try {
            storageService.saveFile(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }
}
