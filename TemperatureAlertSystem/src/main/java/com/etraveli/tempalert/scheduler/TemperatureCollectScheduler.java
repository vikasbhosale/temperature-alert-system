package com.etraveli.tempalert.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.etraveli.tempalert.datasource.UserCityCache;
import com.etraveli.tempalert.datasource.UserCityCache.UserCityData;
import com.etraveli.tempalert.feign.beans.TemperatureData;
import com.etraveli.tempalert.feign.client.TemperatureClient;
import com.etraveli.tempalert.processor.EventProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author VBhosale
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TemperatureCollectScheduler {

	private final TemperatureClient temperatureClient;
	
	private final UserCityCache userCityCache;
	
	private final EventProcessor<UserCityData> cityEventProcessor;
	
	
	/**
	 * 
	 */
	@Scheduled(cron = "${temperature-system.pull.cron}")
	public void collect() {
		log.info("In TemperatureCollectScheduler !!!");
		// Pull the latest feed about temperature
		List<TemperatureData> temperature = temperatureClient.getTemperature();
		// Print
		temperature.stream().forEach(temp -> log.info(temp.toString()));
		// Sort the feed data per city
		Map<String, TemperatureData> cityWiseTemp = temperature.stream().collect(Collectors.toMap(temp -> temp.getCity(), temp -> temp));
		// Pull the user configuration / city data from cache and process the events
		userCityCache.getMap().entrySet()
		.forEach(entry -> {
			// List of user settings per city
			LinkedList<UserCityData> userCityData = entry.getValue();
			// Latest feed of Temperature
			TemperatureData temperatureData = cityWiseTemp.get(entry.getKey());
			// Process data per city in async mode
			cityEventProcessor.process(temperatureData, userCityData);
		});
	}
}
