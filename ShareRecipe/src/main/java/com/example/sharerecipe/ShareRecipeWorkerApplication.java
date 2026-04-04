package com.example.sharerecipe;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShareRecipeWorkerApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ShareRecipeWorkerApplication.class)
				.web(WebApplicationType.NONE)
				.profiles("worker")
				.run(args);
	}
}
