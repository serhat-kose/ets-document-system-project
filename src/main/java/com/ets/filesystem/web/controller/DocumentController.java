package com.ets.filesystem.web.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;

@RestController
@RequestMapping("api/v1/document")
public class DocumentController {

    @GetMapping("/hello")
    public ResponseEntity<?> getHello(){

        return ResponseEntity.ok().body("HELLO DOCUMENT PROJECT");
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void getDocument(@RequestParam("file") MultipartFile file) throws IOException {

        System.out.println("file : " + file.getOriginalFilename());

    }
}
