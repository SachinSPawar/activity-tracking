package com.userexpirior.activitytracking.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userexpirior.activitytracking.dao.ActivityRepository;
import com.userexpirior.activitytracking.entity.Activity;
import com.userexpirior.activitytracking.to.ActivityOccurenceTO;
import com.userexpirior.activitytracking.to.EmployeeActivitiesTO;

@Service
public class ActivityStatisticsService {

	@Autowired
	private ActivityRepository activityRepository;

	public List<ActivityOccurenceTO> getSevenDayStatistics() {

		long epochSecond = LocalDate.now().atStartOfDay().minusDays(7).toInstant(ZoneOffset.of("+05:30"))
				.toEpochMilli();

		return activityRepository.getActivityWiseCountForLastSevenDays(epochSecond);
	}

	public List<EmployeeActivitiesTO> getTodaysActivties() {

		long epochSecond = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.of("+05:30")).toEpochMilli();

		List<Activity> activities = activityRepository.getTodaysActivities(epochSecond);

		Map<Long, EmployeeActivitiesTO> employeeWiseActivitiesMap = new HashMap<>();

		for (Activity activity : activities) {

			EmployeeActivitiesTO employeeActivitiesTO = employeeWiseActivitiesMap.get(activity.getEmployeeId());

			if (employeeActivitiesTO == null) {
				employeeActivitiesTO = new EmployeeActivitiesTO();
				employeeActivitiesTO.setEmployeeId(activity.getEmployeeId());
				employeeActivitiesTO.setActivities(new ArrayList<>());
			}

			employeeActivitiesTO.getActivities().add(activity.convertToTO());

			employeeWiseActivitiesMap.put(activity.getEmployeeId(), employeeActivitiesTO);

		}

		return new ArrayList<>(employeeWiseActivitiesMap.values());
	}

}
