package com.ets.filesystem.service.impl;

import com.ets.filesystem.entity.*;
import com.ets.filesystem.repository.*;
import com.ets.filesystem.service.*;
import com.ets.filesystem.web.controller.*;
import com.ets.filesystem.web.model.File;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

@Service
public class FileStorageServiceImpl  implements FileStorageService {
    private final Path root = Paths.get("uploads");

    @Autowired
    private FileDBRepository fileDBRepository;

    @Override
    public void saveFile(MultipartFile file,String dosyaAdi) {

        try {
            Path targetLocation = this.root.resolve(file.getOriginalFilename());
            System.out.println("targetLocation ::" + targetLocation);

            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            FileDB FileDB = new FileDB(dosyaAdi, file.getContentType(),targetLocation.toString(), file.getBytes());
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
    public FileDB loadFile(String filename) {
        FileDB file= fileDBRepository.findByFilename(filename);
        if (file!=null) {
            return file;
        } else {
            throw new RuntimeException("Could not read the file!");
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
            model.setName(file.getFilename());
            model.setDocType(file.getDocumentType());
            model.setUploadDir(file.getUploadDir());

            String url = MvcUriComponentsBuilder.
                    fromMethodName(DocumentController.class, "getFile", file.getFilename().toString()).build().toString();

            model.setUrl(url);
            modelList.add(model);
        }

        return modelList;
    }

    @Override
    public boolean deleteFileById(Long id) {
       Optional<FileDB> file =  fileDBRepository.findById(id);

       if(file.isPresent()){
           fileDBRepository.deleteById(file.get().getId());
           return true;
       }
       return false;
    }

    @Override
    public File updateFile(MultipartFile file,Long id) {
        try {
            FileDB cuurentFile=checkCurrentFile(id);

            if(cuurentFile!=null){
                Path targetLocation = this.root.resolve(file.getOriginalFilename());
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

                cuurentFile.setData(file.getBytes());
                cuurentFile.setUploadDir(targetLocation.toString());
                cuurentFile.setDocumentType(file.getContentType());
                fileDBRepository.save(cuurentFile);

                return  mapEntityToModel(cuurentFile);
            }
            else {
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not Upload the file. Error: " + e.getMessage());
        }
    }

    private File mapEntityToModel(FileDB cuurentFile) {
        File model = new File();
        model.setUrl(cuurentFile.getUploadDir());
        model.setName(cuurentFile.getFilename());
        model.setDocType(cuurentFile.getDocumentType());
        model.setUploadDir(cuurentFile.getUploadDir());

        return model;
    }

    private FileDB checkCurrentFile(Long id) {
       Optional<FileDB> current = fileDBRepository.findById(id);

        return current.orElse(null);
    }
}
