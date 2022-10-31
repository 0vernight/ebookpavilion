package com.mike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@ServletComponentScan( "com.mike.servlet")      //自定义servlet
//@MapperScan("com.mike.mapper")
public class EbookpavilionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EbookpavilionApplication.class, args);


    }

}
