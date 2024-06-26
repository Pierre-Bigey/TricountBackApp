package com.PierreBigey.TricountBack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TricountBackApplication {

	@GetMapping("/demo")
	public String getResponse() {
		return "Hello the app is working...";
	}

	public static void main(String[] args) {

		SpringApplication.run(TricountBackApplication.class, args);
	}

}
