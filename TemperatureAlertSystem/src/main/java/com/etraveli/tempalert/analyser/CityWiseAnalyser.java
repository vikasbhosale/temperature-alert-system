package com.etraveli.tempalert.analyser;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.etraveli.tempalert.controller.UserRestURIConstants;
import com.etraveli.tempalert.datasource.UserCityCache;
import com.etraveli.tempalert.model.User;

import lombok.RequiredArgsConstructor;

/**
 * Analyze the user settings and classify them per city.
 * Cache the data in Map of Key - City, Value - LinkedList<UserCityData>>
 * 
 * @author VBhosale
 *
 */
@Component
@RequiredArgsConstructor
public class CityWiseAnalyser implements BaseAnalyser {

	private final UserCityCache userCityCache;
	
	@Override
	public void process(User user, String action) {
		CompletableFuture.runAsync(() -> {
			if (UserRestURIConstants.CREATE_USER.equals(action) || UserRestURIConstants.UPDATE_USER.equals(action)) {
				userCityCache.put(user);
			} else if (UserRestURIConstants.DELETE_USER.equals(action)) {
				userCityCache.remove(user);
			}
		});
	}

}
