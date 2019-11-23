# Activity Tracking 

**Database configuration**

Change following properties in application.properties

* spring.datasource.url=jdbc:postgresql://localhost:5432/activitytracking 
* spring.datasource.username= <username>
* spring.datasource.password= <password>
* spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL9Dialect 



**To build jar:**

```bash
mvn clean package
```
**To start service:**

```bash
java -jar activity-tracking-0.0.1-SNAPSHOT.jar directory/for/data/files
```

**REST API:**

**URL:** 
GET http://localhost:8080/activity-tracking/api/statistics

**RESPONSE:**
```json
{
  "todays_activities": [
    {
      "activities": [
        {
          "name": "login",
          "time": 1574521855000
        }
      ],
      "employee_id": 1
    }
  ],
  "last_7_day_statistics": [
    {
      "occurrences": 1,
      "activity_name": "login"
    }
  ]
}
```
