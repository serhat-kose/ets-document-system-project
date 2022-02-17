package com.ets.filesystem.repository;

import com.ets.filesystem.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB,String> {
}
