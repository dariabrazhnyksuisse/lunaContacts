package com.lunacontacts.application;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {
    /**
     *  library -> to perform entity-com.lunacontacts.application.DTO conversion - ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
