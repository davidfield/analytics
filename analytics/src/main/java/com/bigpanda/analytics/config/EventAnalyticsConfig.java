package com.bigpanda.analytics.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventAnalyticsConfig {
	
	@Bean(name = "eventQueue")
	public BlockingQueue<String> queue() {
	    return new LinkedBlockingQueue<>();
	}
	
	@Bean(name = "eventTypeCounts")
	public  Map<String, Integer> eventTypeCounts() {
		return new HashMap<>();		
	}

	@Bean(name = "dataWordCounts")
	public  Map<String, Integer> dataWordCounts() {
		return new HashMap<>();		
	}

}
