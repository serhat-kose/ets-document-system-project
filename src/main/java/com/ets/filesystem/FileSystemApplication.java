package com.ets.filesystem;


import com.ets.filesystem.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.*;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ETS Document System API", version = "2.0", description = "ETS TUR Case Document System API Operations"))
@SecurityScheme(name = "etsdocapi", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
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
