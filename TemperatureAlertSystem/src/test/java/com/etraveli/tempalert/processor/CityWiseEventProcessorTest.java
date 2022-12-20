package com.etraveli.tempalert.processor;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.etraveli.tempalert.datasource.UserCityCache.UserCityData;
import com.etraveli.tempalert.feign.beans.TemperatureData;
import com.etraveli.tempalert.notifications.NotificationService;

/**
 * @author VBhosale
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CityWiseEventProcessorTest {

	@Mock
	private NotificationService notificationService;
	
	private CityWiseEventProcessor cityWiseEventProcessor;
	
	@Before
	public void setUp() {
		cityWiseEventProcessor = new CityWiseEventProcessor(notificationService);
	}
	
	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testProcess_SingleNotification() throws InterruptedException {
		TemperatureData temperatureData = new TemperatureData("Mumbai", BigDecimal.valueOf(8.12), "celsius");
		LinkedList<UserCityData> mumbaiDatas = new LinkedList<>();
		mumbaiDatas.add(new UserCityData(0, "Vikas", "Bhosale", "abc@gmail.com", "Mumbai", "gt", BigDecimal.valueOf(30.2)));
		mumbaiDatas.add(new UserCityData(0, "Anurag", "A", "efd@gmail.com", "Mumbai", "gt", BigDecimal.valueOf(50.2)));
		mumbaiDatas.add(new UserCityData(0, "Sushant", "K", "xyz@gmail.com", "Mumbai", "ls", BigDecimal.valueOf(10.2)));
		cityWiseEventProcessor.process(temperatureData, mumbaiDatas);
		// process method start new thread which takes little time to complete.
		// So sleep for 0.1 sec
		Thread.sleep(100);
		verify(notificationService, only()).sendNotification(Mockito.anyString());
	}
	
	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testProcess_MultipleNotification() throws InterruptedException {
		TemperatureData temperatureData = new TemperatureData("Mumbai", BigDecimal.valueOf(41.01), "celsius");
		LinkedList<UserCityData> mumbaiDatas = new LinkedList<>();
		mumbaiDatas.add(new UserCityData(0, "Vikas", "Bhosale", "abc@gmail.com", "Mumbai", "gt", BigDecimal.valueOf(30.2)));
		mumbaiDatas.add(new UserCityData(0, "Anurag", "A", "efd@gmail.com", "Mumbai", "gt", BigDecimal.valueOf(20.2)));
		mumbaiDatas.add(new UserCityData(0, "Sushant", "K", "xyz@gmail.com", "Mumbai", "ls", BigDecimal.valueOf(10.2)));
		cityWiseEventProcessor.process(temperatureData, mumbaiDatas);
		// process method start new thread which takes little time to complete.
		// So sleep for 0.1 sec
		Thread.sleep(100);
		verify(notificationService, times(2)).sendNotification(Mockito.anyString());
	}
}
