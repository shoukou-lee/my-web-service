package com.shoukou.mywebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWebServiceApplication.class, args);
    }

}
