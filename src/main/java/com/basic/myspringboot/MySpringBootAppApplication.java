package com.basic.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MySpringBootAppApplication.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);
	}
	@Bean
	public String hello(){
		return "hello SpringBoot";

	}

}