package com.mx.EnvioDeCorreos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EnvioDeCorreosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnvioDeCorreosApplication.class, args);
	}

}
