package com.ets.filesystem;

import com.ets.filesystem.entity.*;
import com.ets.filesystem.repository.*;
import com.ets.filesystem.service.*;
import com.google.common.collect.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.*;

import javax.annotation.*;
import java.security.acl.*;
import java.util.*;

@SpringBootApplication
@EnableSwagger2
//@OpenAPIDefinition(info = @Info(title = "FileSystem API", version = "2.0", description = "Files Information"))
//@SecurityScheme(name = "fileapi", scheme = "Bearer", type =SecuritySchemeType.APIKEY , in = SecuritySchemeIn.HEADER)
public class FileSystemApplication {

	@Resource
	FileStorageService storageService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsRepository userDetailsRepository;

	public static void main(String[] args) {
		SpringApplication.run(FileSystemApplication.class, args);
	}
	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(Lists.newArrayList(apiKey()))
				.apiInfo(apiInfo());

	}

	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(
				"test-app-client-id",
				"test-app-client-secret",
				"test-app-realm",
				"test-app",
				"",
				ApiKeyVehicle.HEADER,
				"Authorization",
				"," /*scope separator*/);
	}

	@Bean
	SecurityScheme apiKey() {
		return new ApiKey("token", "token", "header");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Spring REST Sample with Swagger")
				.description("Spring REST Sample with Swagger")
				.version("2.0")
				.build();
	}
	@PostConstruct
	protected void init(){
		storageService.deleteAll();
		storageService.init();
		List<Authority> authorityList = new ArrayList<>();

		authorityList.add(createAuthority("USER","User Role"));
		authorityList.add(createAuthority("ADMIN","Admin Role"));

		User user = new User();

		user.setUserName("serhat");
		user.setFirstName("skose");
		user.setLastName("Köse");
		user.setEmail("serhat1@gmail.com");
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode("12345"));
		user.setAuthorities(authorityList);

		userDetailsRepository.save(user);

	}

	private Authority createAuthority(String roleCode,String roleDesc){

		Authority authority = new Authority();

		authority.setRoleCode(roleCode);
		authority.setRoleDesc(roleDesc);

		return authority;
	}


}
