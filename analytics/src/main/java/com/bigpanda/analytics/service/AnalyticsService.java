package com.bigpanda.analytics.service;

import java.util.Map;
import java.util.Optional;

import com.bigpanda.domain.Event;

public interface AnalyticsService extends Runnable{

	Optional<Event> getEvent(String json);

	void addEventDataToStatistics(Event event);

	Map<String, Integer> getEventTypeCounts();

	Map<String, Integer> getDataWordCounts();

}