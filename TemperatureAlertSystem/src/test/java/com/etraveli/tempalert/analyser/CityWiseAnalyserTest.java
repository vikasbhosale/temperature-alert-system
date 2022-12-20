package com.etraveli.tempalert.analyser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.etraveli.tempalert.controller.UserRestURIConstants;
import com.etraveli.tempalert.datasource.UserCityCache;
import com.etraveli.tempalert.model.User;

@RunWith(MockitoJUnitRunner.class)
public class CityWiseAnalyserTest {

	private CityWiseAnalyser cityWiseAnalyser;
	
	@Mock
	private User user;
	
	@Mock
	private UserCityCache userCityCache;
	
	@Before
	public void init() {
		cityWiseAnalyser = new CityWiseAnalyser(userCityCache);
	}
	
	@Test
	public void testProcess_CreateUser() throws InterruptedException {
		cityWiseAnalyser.process(user, UserRestURIConstants.CREATE_USER);
		Thread.sleep(100);
		Mockito.verify(userCityCache, Mockito.atLeastOnce()).put(user);
	}
	
	@Test
	public void testProcess_UpdateUser() throws InterruptedException {
		cityWiseAnalyser.process(user, UserRestURIConstants.UPDATE_USER);
		Thread.sleep(100);
		Mockito.verify(userCityCache, Mockito.atLeastOnce()).put(user);
	}
	
	@Test
	public void testProcess_DeleteUser() throws InterruptedException {
		cityWiseAnalyser.process(user, UserRestURIConstants.DELETE_USER);
		Thread.sleep(100);
		Mockito.verify(userCityCache, Mockito.atLeastOnce()).remove(user);
	}
}
