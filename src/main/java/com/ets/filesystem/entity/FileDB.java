package com.ets.filesystem.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "files")
@Getter
@Setter
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "upload_dir")
    private String uploadDir;


    @Lob
    private byte[] data;

    public FileDB() {
    }

    public FileDB(String fileName, String documentType, String uploadDir, byte[] data) {

        this.fileName = fileName;
        this.documentType = documentType;
        this.uploadDir = uploadDir;
        this.data = data;
    }
}
