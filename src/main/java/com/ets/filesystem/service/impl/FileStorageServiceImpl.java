package com.ets.filesystem.service.impl;

import com.ets.filesystem.entity.*;
import com.ets.filesystem.repository.*;
import com.ets.filesystem.service.*;
import com.ets.filesystem.web.controller.*;
import com.ets.filesystem.web.model.File;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path root = Paths.get("uploads");

    @Autowired
    private FileDBRepository fileDBRepository;

    @Override
    public void saveFile(MultipartFile file) {

        try {
            Path targetLocation = this.root.resolve(file.getOriginalFilename());
            System.out.println("targetLocation ::" + targetLocation);

            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            FileDB FileDB = new FileDB(fileName, file.getContentType(),targetLocation.toString(), file.getBytes());
             fileDBRepository.save(FileDB);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Resource loadFile(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAllFiles() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public List<File> getAllFiles() {
        List<File> modelList = new ArrayList<>();
        List<FileDB> list = fileDBRepository.findAll();

        for (FileDB file:list) {
            File model = new File();
            model.setName(file.getFileName());
            model.setDocType(file.getDocumentType());
            model.setUploadDir(file.getUploadDir());
            model.setData(file.getData());

        String url = MvcUriComponentsBuilder.
                fromMethodName(DocumentController.class, "getFile", file.getFileName().toString()).build().toString();

            model.setUrl(url);
            modelList.add(model);
        }

        return modelList;
    }
}
