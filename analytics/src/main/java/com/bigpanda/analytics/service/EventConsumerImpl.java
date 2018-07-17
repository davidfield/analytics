package com.bigpanda.analytics.service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bigpanda.domain.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EventConsumerImpl implements EventConsumer {

	@Autowired
	private BlockingQueue<String> blockingQueue;
	
	@Autowired
	private Map<String, Integer> eventTypeCounts;
	
	@Autowired
	private Map<String, Integer> dataWordCounts;

	@Value("${event.queue.poisonpill.value}")
	private String poisonPillValue;


	private Optional<Event> getEvent(String json) {
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

	private void addEventDataToStatistics(Event event) {		
		BiFunction<String, Integer, Integer> biFunction = (k, v) -> v + 1;
		
		eventTypeCounts.computeIfAbsent(event.getEvent_type(), k -> new Integer(1));
		eventTypeCounts.computeIfPresent(event.getEvent_type(), biFunction);
		
		dataWordCounts.computeIfAbsent(event.getEvent_type(), k -> new Integer(1));
		dataWordCounts.computeIfPresent(event.getEvent_type(), biFunction);
	}
	

	public void process() {
		while (true)
		{
			System.out.println("PROCESS: "+Thread.currentThread().getName());
			String json = null;;
			try {
				json = blockingQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (json.equals(poisonPillValue)) {
				break;
			} else {
				Optional<Event> event = getEvent(json);
				if (!event.isPresent()) {
					// LOG BAD MESSAGE
				} else {
					addEventDataToStatistics(event.get());
				}
			}
		}
	}

	@Override
	public void run() {
		System.out.println("RUN: "+Thread.currentThread().getName());
		process();
	}
}
