package com.korea.babchingu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BabchinguApplication {

	public static void main(String[] args) {
		SpringApplication.run(BabchinguApplication.class, args);
	}

}
