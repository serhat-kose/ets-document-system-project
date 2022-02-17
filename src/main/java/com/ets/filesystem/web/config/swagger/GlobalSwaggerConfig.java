package com.ets.filesystem.web.config.swagger;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;



public class GlobalSwaggerConfig {
    /*
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.ets.filesystem.web.web.controller")
                )
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo());
    }



    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("ETS DOCUMENT SYSTEM REST API")
                .description(" ETS Document System Project API TEST")
                .contact(new Contact("Serhat Köse", "www.etstur.com", "serhatkse94@gmail.com"))
                .license("ets 1.0")
                .version("1.0.0")
                .build();
    }
}

     */
}
