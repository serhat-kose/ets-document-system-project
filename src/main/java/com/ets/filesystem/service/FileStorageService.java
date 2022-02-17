package com.ets.filesystem.service;

import org.springframework.core.io.*;
import org.springframework.web.multipart.*;

import java.nio.file.*;
import java.util.stream.*;

public interface FileStorageService {
    void saveFile(MultipartFile file);
    public void init();
    public Resource load(String filename);
    public void deleteAll();
    public Stream<Path> loadAll();
}
