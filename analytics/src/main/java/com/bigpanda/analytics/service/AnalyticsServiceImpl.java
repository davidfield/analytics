package com.bigpanda.analytics.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bigpanda.domain.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	@Value("${event.generator.name}")
	private String generatorName;

	@Value("${event.generator.path}")
	private String generatorPath;

	private Map<String, Integer> eventTypeCounts = new HashMap<>();
	private Map<String, Integer> dataWordCounts = new HashMap<>();

	private void process() throws IOException {
		BufferedReader reader = getGeneratorReader();
		String s = reader.readLine();
		while (s != null) {
			Optional<Event> event = getEvent(s);
			if (event.isPresent()) {
				addEventDataToStatistics(event.get());
			}
			s = reader.readLine();
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
	public Optional<Event> getEvent(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Event e = null;
		try {
			e = mapper.readValue(json, Event.class);
		} catch (com.fasterxml.jackson.core.JsonParseException e2) {
			return Optional.empty();

		} catch (IOException e1) {
			return Optional.empty();
		}
		return Optional.of(e);
	}

	public void addEventDataToStatistics(Event event) {
		eventTypeCounts.merge(event.getEvent_type(), 1, Integer::sum);
		dataWordCounts.merge(event.getData(), 1, Integer::sum);
	}

	@Override
	public Map<String, Integer> getEventTypeCounts() {
		return Collections.unmodifiableMap(eventTypeCounts);
	}

	@Override
	public Map<String, Integer> getDataWordCounts() {
		return Collections.unmodifiableMap(dataWordCounts);
	}

	@Override
	public void run() {
		try {
			this.process();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
