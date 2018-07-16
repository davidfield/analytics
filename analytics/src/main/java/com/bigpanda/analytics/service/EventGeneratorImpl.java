package com.bigpanda.analytics.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bigpanda.domain.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EventGeneratorImpl implements EventGenerator {
	
	@Value("${event.generator.name}")
	private String generatorName;

	@Value("${event.generator.path}")
	private String generatorPath;
	
	@Value("${event.queue.poisonpill.value}")
	private String poisonPillValue;
	
	@Value("${event.consumers.no}")
	private int numberOfEventConsumers;
	
	@Autowired
	private BlockingQueue<String> blockingQueue;

	public void process() throws IOException {
		BufferedReader reader = getGeneratorReader();
		String s = reader.readLine();
		while (s != null) {
			blockingQueue.add(s);
			s = reader.readLine();
		}
		
		// No more events, send poison pill to all consumers
		for (int i = 0; i<numberOfEventConsumers; i++) {
			blockingQueue.add(poisonPillValue);
		}
	}

	private BufferedReader getGeneratorReader() throws IOException {
		ProcessBuilder builder = new ProcessBuilder(generatorName);
		builder.directory(new File(generatorPath));
		Process process = builder.start();
		InputStream stdout = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
		return reader;
	}
	

	@Override
	public void run() {
		try {
			process();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
