package com.etraveli.tempalert.datasource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.etraveli.tempalert.model.TemperatureSetting;
import com.etraveli.tempalert.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Cache utility class which carries the cache of user settings per city.
 *  
 * @author VBhosale
 *
 */
@Component
public class UserCityCache {
	
	/**
	 * Cache in Map like Key - CityName, Value - List of user's settings per city.
	 * Example :
	 * User A
	 * 	Settings - 
	 * 		Alert me on Mumbai temperature goes below 10 degree.
	 * 		Alert me on Delhi temperature goes above 20 degree.
	 * User B
	 * 	Settings - 
	 * 		Alert me on Delhi temperature goes above 15 degree.
	 * 
	 * Map will be
	 * [Mumbai, [User A, City - Mumbai, Alert me on Mumbai temperature goes below 10 degree]]
	 * [Delhi, [User a, City - Delhi, Alert me on Delhi temperature goes above 20 degree.]
	 * 			[User B, City - Delhi, Alert me on Delhi temperature goes above 15 degree.]] 
	 */
	private Map<String, LinkedList<UserCityData>> cityCache = new ConcurrentHashMap<>();
	
	/**
	 * @param cityName
	 * @return
	 */
	public List<UserCityData> get(String cityName) {
		return cityCache.get(cityName);
	}
	
	/**
	 * @return
	 */
	public Map<String, LinkedList<UserCityData>> getMap() {
		return cityCache;
	}
	
	/**
	 * @return
	 */
	public List<LinkedList<UserCityData>> getAll() {
		return cityCache.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
	}
	
	/**
	 * @param user
	 * @return
	 */
	public void put(User user) {
		user.getTemperatureSettings()
		.stream().forEach(temp -> {
		if (cityCache.get(temp.getCity()) == null) {
			LinkedList<UserCityData> newData = new LinkedList<>();
			newData.add(buildUserCityData(user, temp));
			cityCache.put(temp.getCity(), newData);
		} else {
			cityCache.get(temp.getCity())
			.add(buildUserCityData(user, temp));
		}
		});
	}
	
	/**
	 * @param user
	 * @param temp
	 * @return
	 */
	private UserCityData buildUserCityData(User user, TemperatureSetting temp) {
		return new UserCityData().builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.city(temp.getCity())
				.condition(temp.getCondition())
				.reading(temp.getReading())
			.build();
	}
	
	/**
	 * @param cityName
	 */
	public void remove(String cityName) {
		cityCache.remove(cityName);
	}
	
	/**
	 * @param cityName
	 */
	public void remove(User user) {
		user.getTemperatureSettings()
		.stream().forEach(temp -> {
			if (cityCache.get(temp.getCity()) == null) {
				// New city which not exist in DB.
			} else {
				cityCache.get(temp.getCity())
				.remove(buildUserCityData(user, temp));
			}
		});
	}
	
	/**
	 * @param cityName
	 * @param userCityData
	 */
	public void removeUserSetting(String cityName, UserCityData userCityData) {
		cityCache.get(cityName).remove(userCityData);
	}
	
	/**
	 * Internal Cache object for storage and processing.
	 *  
	 * @author VBhosale
	 *
	 */
	@Getter
	@Setter
	@Builder
	@EqualsAndHashCode
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserCityData {
		private long id;
		private String firstName;
		private String lastName;
		private String email;
		private String city;
		private String condition;
		private BigDecimal reading;
	}
}
