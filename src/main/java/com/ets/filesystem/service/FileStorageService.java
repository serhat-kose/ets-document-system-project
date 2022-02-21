package com.ets.filesystem.service;

import com.ets.filesystem.web.model.*;
import org.springframework.core.io.*;
import org.springframework.web.multipart.*;

import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public interface FileStorageService {

    void saveFile(MultipartFile file);
    public void init();
    public Resource loadFile(String filename);
    public void deleteAll();
    public Stream<Path> loadAllFiles();
    public List<File> getAllFiles();
}
