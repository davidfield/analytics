package com.bigpanda.analytics;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bigpanda.analytics.service.EventConsumer;
import com.bigpanda.analytics.service.EventGenerator;

@SpringBootApplication
public class EventAnalyticsApplication {

	@Autowired
	private EventConsumer eventConsumer;

	@Autowired
	private EventGenerator eventGenerator;

	public static void main(String[] args) {
		SpringApplication.run(EventAnalyticsApplication.class, args);
	}

	@Value("${event.consumers.no}")
	private int noOfEventConsumers;

	@PostConstruct
	public void run() throws IOException {
		ExecutorService producerService = Executors.newSingleThreadExecutor();
		producerService.execute(eventGenerator);
		producerService.shutdown();

		ExecutorService consumerService = Executors.newFixedThreadPool(noOfEventConsumers);
		for (int i = 0; i < noOfEventConsumers; i++) {
			consumerService.execute(eventConsumer);
		}
		consumerService.shutdown();
	}

}
