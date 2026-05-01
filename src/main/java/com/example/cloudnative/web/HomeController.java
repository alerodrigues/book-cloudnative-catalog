package com.example.cloudnative.web;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudnative.config.PolarProperties;

@RestController
public class HomeController {
	private final PolarProperties polarProperties;
	private Environment environment;
	
	public HomeController(PolarProperties polarProperties, Environment environment) {
		this.polarProperties = polarProperties;
		this.environment = environment;
	}

	@GetMapping("/home1")
	public String getGreeting1() {
		return polarProperties.getGreeting();
	}
	
	@GetMapping("/home2")
	public String getGreeting2() {
		return environment.getProperty("polar.greeting");
	}

}
