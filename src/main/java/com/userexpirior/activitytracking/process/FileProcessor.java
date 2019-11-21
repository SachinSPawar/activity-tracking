package com.userexpirior.activitytracking.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.userexpirior.activitytracking.dao.ActivityRepository;
import com.userexpirior.activitytracking.entity.Activity;
import com.userexpirior.activitytracking.to.EmployeeActivitiesTO;

@Component
public class FileProcessor {

	@Autowired
	private ActivityRepository activityRepository;

	private static final List<String> ACTIVITIES;

	static {
		String[] array = { "login", "logout", "teabreak", "lunchbreak", "gamemood", "naptime" };

		ACTIVITIES = Arrays.asList(array);

	}

	public void processFile(File file) throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		Gson gson = new Gson();

		EmployeeActivitiesTO activityList = gson.fromJson(new FileReader(file), EmployeeActivitiesTO.class);

		List<Activity> activities = activityList.getActivities().stream()
				.map(activity -> new Activity(activityList.getEmployeeId(), activity)).collect(Collectors.toList());

		List<Activity> validActivities = activities.stream()
				.filter(activity -> ACTIVITIES.contains(activity.getName().toLowerCase())).collect(Collectors.toList());
		;

		activityRepository.saveAll(validActivities);

	}

}
