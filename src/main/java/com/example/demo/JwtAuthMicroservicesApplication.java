package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class JwtAuthMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthMicroservicesApplication.class, args);
		
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//        System.out.println("admin123 -> " + encoder.encode("admin123"));
//        System.out.println("rohit123 -> " + encoder.encode("rohit123"));
	}

}
