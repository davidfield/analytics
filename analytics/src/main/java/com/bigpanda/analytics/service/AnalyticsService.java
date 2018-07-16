package com.bigpanda.analytics.service;

import java.util.Map;

public interface AnalyticsService {
	
	Map<String, Integer> getEventTypeCounts();

	Map<String, Integer> getDataWordCounts();

}
