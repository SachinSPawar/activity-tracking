package com.userexpirior.activitytracking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.userexpirior.activitytracking.entity.Activity;
import com.userexpirior.activitytracking.to.ActivityOccurenceTO;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query(value = "select new com.userexpirior.activitytracking.to.ActivityOccurenceTO(a.name,count(*)) from Activity a group by a.name")
	public List<ActivityOccurenceTO> getActivityWiseCountForLastSevenDays();

	@Query(value = "select a from Activity a where a.time>=:time")
	public List<Activity> getTodaysActivities(@Param("time") Long time);

}
