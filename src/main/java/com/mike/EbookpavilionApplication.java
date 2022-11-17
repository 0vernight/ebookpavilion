package com.mike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;


@SpringBootApplication
@ServletComponentScan( "com.mike.servlet")      //自定义servlet
//@MapperScan("com.mike.mapper")
public class EbookpavilionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EbookpavilionApplication.class, args);
        //获取系统变量
        ConfigurableEnvironment environment = run.getEnvironment();
        String property = environment.getProperty("os.name");
        String username = environment.getProperty("username");
        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
        System.out.println("systemEnvironment==>"+username+":"+property);
        System.out.println(systemEnvironment);

    }

}
