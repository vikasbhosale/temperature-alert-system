package com.etraveli.tempalert.processor;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.etraveli.tempalert.datasource.UserCityCache.UserCityData;
import com.etraveli.tempalert.enums.Operator;
import com.etraveli.tempalert.feign.beans.TemperatureData;
import com.etraveli.tempalert.notifications.NotificationService;

import lombok.RequiredArgsConstructor;

/**
 * @author VBhosale
 *
 */
@Component
@RequiredArgsConstructor
public class CityWiseEventProcessor implements EventProcessor<UserCityData> {

	private final NotificationService notificationService;
	
	@Override
	public void process(TemperatureData temperatureData, LinkedList<UserCityData> userSettings) {
		CompletableFuture.runAsync(() -> {
			userSettings.stream().forEach(setting -> {
				BigDecimal reading = temperatureData.getReading();
				if (setting.getCondition().equalsIgnoreCase(Operator.GT.name()) && 
						setting.getReading().compareTo(reading) == -1) {
					notificationService.sendNotification(String.format("User %s - critera: %s %s, Setting : Current Temperature : %s, City: %s", 
							setting.getFirstName(), setting.getCondition(), setting.getReading(), reading ,setting.getCity()));
				} else if (setting.getCondition().equalsIgnoreCase(Operator.LS.name()) &&
						setting.getReading().compareTo(reading) == 1) {
					notificationService.sendNotification(String.format("User %s - critera: %s %s, Setting : Current Temperature : %s, City: %s", 
							setting.getFirstName(), setting.getCondition(), setting.getReading(), reading ,setting.getCity()));
				} else if (setting.getCondition().equalsIgnoreCase(Operator.EQ.name()) &&
						setting.getReading().compareTo(reading) == 0) {
					notificationService.sendNotification(String.format("User %s - critera: %s %s, Setting : Current Temperature : %s, City: %s", 
							setting.getFirstName(), setting.getCondition(), setting.getReading(), reading ,setting.getCity()));
				}
			});
		});
	}

}
