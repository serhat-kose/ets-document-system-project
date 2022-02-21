package com.ets.filesystem.repository;

import com.ets.filesystem.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB,Long> {

    FileDB findByFilename(String fileName);
}
