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
    private Long id;

    @Column(name = "name")
    private String filename;

    @Column(name = "type")
    private String documentType;

    @Column(name = "upload_dir")
    private String uploadDir;

    @Column
    private byte[] data;

    public FileDB() {
    }

    public FileDB(String filename, String documentType, String uploadDir, byte[] data) {

        this.filename = filename;
        this.documentType = documentType;
        this.uploadDir = uploadDir;
        this.data = data;
    }
}
