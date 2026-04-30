package com.example.cloudnative;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("home")
public class HomeController {

	@GetMapping
	public String home() {
		return "Hello World!";
	}
	
}
