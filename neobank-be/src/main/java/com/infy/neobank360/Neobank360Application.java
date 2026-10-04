package com.infy.neobank360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.infy.neobank360.security.JwtProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class Neobank360Application {

	public static void main(String[] args) {
		SpringApplication.run(Neobank360Application.class, args);
	}
}
