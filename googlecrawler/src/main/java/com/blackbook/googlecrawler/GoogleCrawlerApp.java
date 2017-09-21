package com.blackbook.googlecrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
@EnableSwagger2
public class GoogleCrawlerApp {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Google crawler API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api.*"))
                .build();
    }

    @Bean
    public ScheduledExecutorService schedulerService(){
        return Executors.newSingleThreadScheduledExecutor();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Black Book Bot REST API for Google crawler")
                .description("Methods for reading info from google books")
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GoogleCrawlerApp.class, args);
    }
}
