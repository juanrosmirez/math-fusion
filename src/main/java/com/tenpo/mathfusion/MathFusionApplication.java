package com.tenpo.mathfusion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@ComponentScan(basePackages = "com.tenpo.mathfusion")
public class MathFusionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MathFusionApplication.class, args);
	}
	
}
