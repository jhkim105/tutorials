package com.example.beanio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ProtocolProperties.class)
public class BeanioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeanioApplication.class, args);
	}


}
