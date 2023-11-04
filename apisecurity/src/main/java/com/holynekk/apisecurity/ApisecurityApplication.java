package com.holynekk.apisecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableJpaRepositories("com.holynekk.apisecurity.*")
//@EntityScan("com.holynekk.apisecurity.*")
public class ApisecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApisecurityApplication.class, args);
	}

}
