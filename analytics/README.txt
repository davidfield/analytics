The application is developed using Spring Boot.
https://spring.io/projects/spring-boot

To run in IDE:
Run the com.bigpanda.analytics.EventAnalyticsApplication main method as 'Spring Boot App' 
or Java Application'.

The name and path of the event generator are both defined in the file application.properties.
Edit these as required.

The following REST resources are defined:
/event/analytics/types
/event/analytics/datawords

Areas for improvements:
1. Unit tests for the methods defined in AnalyticsService.
2. Logging of all processed events.
3. Logging of exceptions.
4. Add another REST endpoint to show the number of invalid events, as well as the % of invalid events
to valid events.
5. Currently, the AnalyticsService runs in one thread. While the file has to be read in one thread, additional
worker threads could be used to process the events once they have been read. One approach could be to use a 
a blocking queue and ConncurrentHashMap, and replace Map.merge with the atomic Map.computeIfAbsent 
and Map.computeIfPresent methods. 