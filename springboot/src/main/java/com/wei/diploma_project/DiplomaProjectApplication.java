package com.wei.diploma_project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.wei.diploma_project.mapper")
@SpringBootApplication
public class DiplomaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaProjectApplication.class, args);
    }

}
