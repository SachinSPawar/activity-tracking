package com.userexpirior.activitytracking.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userexpirior.activitytracking.service.ActivityStatisticsService;

@RestController
@RequestMapping("/activity-tracking/api")
public class ActivityResource {

	@Autowired
	private ActivityStatisticsService statisticsService;

	@GetMapping("/statistics")
	public ResponseEntity<Object> getActivityStatisitics() {

		Map<String, Object> response = new HashMap<>();

		response.put("last_7_day_statistics", statisticsService.getSevenDayStatistics());
		response.put("todays_activities", statisticsService.getTodaysActivties());

		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

}
