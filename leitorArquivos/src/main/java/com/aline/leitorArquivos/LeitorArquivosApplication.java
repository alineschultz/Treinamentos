package com.aline.leitorArquivos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
public class LeitorArquivosApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeitorArquivosApplication.class, args);
	}

}
