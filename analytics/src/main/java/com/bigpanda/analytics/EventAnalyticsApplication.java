package com.bigpanda.analytics;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bigpanda.analytics.service.AnalyticsService;

@SpringBootApplication
public class EventAnalyticsApplication {
	
	@Autowired
	private AnalyticsService analyticsService;

	public static void main(String[] args) {
		SpringApplication.run(EventAnalyticsApplication.class, args);
	}

	
	@PostConstruct
	public void run() throws IOException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(analyticsService);
		executorService.shutdown();
	}
	
	
}
