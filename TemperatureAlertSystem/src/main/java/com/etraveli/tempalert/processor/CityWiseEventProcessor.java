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
					notificationService.sendNotification(formatNotificationText(setting, reading));
				} else if (setting.getCondition().equalsIgnoreCase(Operator.LS.name()) &&
						setting.getReading().compareTo(reading) == 1) {
					notificationService.sendNotification(formatNotificationText(setting, reading));
				} else if (setting.getCondition().equalsIgnoreCase(Operator.EQ.name()) &&
						setting.getReading().compareTo(reading) == 0) {
					notificationService.sendNotification(formatNotificationText(setting, reading));
				}
			});
		});
	}
	
	private synchronized String formatNotificationText(UserCityData userSetting, BigDecimal currentTemp) {
		return String.format("\n=================================================================================== \r\n"
				+ "Hi %s,\r\n"
				+ "\r\n"
				+ "ALERT - Temperature touched %s Degree Celsius in %s.\r\n"
				+ "Current Temperature noted - %s \r\n"
				+ "\r\n"
				+ "You requested to alert on temperature %s %s\r\n"
				+ "\r\n"
				+ "Thanks,\r\n"
				+ "Temperature Alert System \r\n"
				+ "=================================================================================== \r\n", 
				userSetting.getFirstName(),
				userSetting.getReading(),
				userSetting.getCity(),
				currentTemp.setScale(2, BigDecimal.ROUND_UP),
				Operator.findByValue(userSetting.getCondition().toUpperCase()).getDesciption(),
				userSetting.getReading());
	}

}
