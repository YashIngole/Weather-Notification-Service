package com.example.weat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.example.weat", "com.example.MailConfig"})
public class WeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatApplication.class, args);
    }

   
}