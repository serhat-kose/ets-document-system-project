package com.ets.filesystem.service;

import com.ets.filesystem.entity.*;
import com.ets.filesystem.web.model.*;
import org.springframework.core.io.*;
import org.springframework.web.multipart.*;

import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public interface FileStorageService {

    void saveFile(MultipartFile file,String dosyaAdi);
    public void init();
    public FileDB loadFile(String filename);
    public void deleteAll();
    public Stream<Path> loadAllFiles();
    public List<File> getAllFiles();

    boolean deleteFileById(Long id);

    File updateFile(MultipartFile file,Long id);
}
