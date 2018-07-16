package com.bigpanda.analytics.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{
	
	@Autowired
	private Map<String, Integer> eventTypeCounts;
	
	@Autowired
	private Map<String, Integer> dataWordCounts;

	@Override
	public Map<String, Integer> getEventTypeCounts() {
		return Collections.unmodifiableMap(eventTypeCounts);
	}

	@Override
	public Map<String, Integer> getDataWordCounts() {
		return Collections.unmodifiableMap(dataWordCounts);
	}
	
}
