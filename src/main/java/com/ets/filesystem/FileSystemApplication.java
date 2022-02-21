package com.ets.filesystem;

import com.ets.filesystem.entity.*;
import com.ets.filesystem.repository.*;
import com.ets.filesystem.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.*;


import javax.annotation.*;
import java.security.acl.*;
import java.util.*;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Document System API", version = "2.0", description = "ETSTUR Case Document System API Operations"))
@SecurityScheme(name = "filesystemapi", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class FileSystemApplication {

	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(FileSystemApplication.class, args);
	}

	@PostConstruct
	protected void init(){
		storageService.deleteAll();
		storageService.init();

	}




}
