package com.ets.filesystem;

import com.ets.filesystem.entity.*;
import com.ets.filesystem.repository.*;
import com.ets.filesystem.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

import javax.annotation.*;
import java.security.acl.*;

@SpringBootApplication
public class FileSystemApplication {

	@Resource
	FileStorageService storageService;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(FileSystemApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(){
		return args -> {

			storageService.deleteAll();
			storageService.init();
			// username: user password: user

			userRepository.save(new User("user",
					"$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi",
					"USER"));
// username: admin password: admin
			userRepository.save(new User("admin",
					"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG",
					"ADMIN"));

		};
	}

}
