package com.thoughtworks.CartApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class CartAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartAppApplication.class, args);
	}

}
