package com.example.sharerecipe;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.example.sharerecipe.config.AppProfiles;

@SpringBootApplication
@EnableScheduling
public class ShareRecipeWorkerApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ShareRecipeWorkerApplication.class)
				.web(WebApplicationType.NONE)
				.profiles(AppProfiles.WORKER)
				.run(args);
	}
}
