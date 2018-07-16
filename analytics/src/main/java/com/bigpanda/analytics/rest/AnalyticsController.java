package com.bigpanda.analytics.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigpanda.analytics.service.AnalyticsService;
import com.bigpanda.analytics.service.AnalyticsServiceImpl;

@RestController
@RequestMapping("/event/analytics")
public class AnalyticsController {

	@Autowired
	private AnalyticsService analyticsService;

	@GetMapping("/types")
	public Map<String, Integer> types() {
		return analyticsService.getEventTypeCounts();
	}

	@GetMapping("/datawords")
	public Map<String, Integer> dataWords() {
		return analyticsService.getDataWordCounts();
	}
}
