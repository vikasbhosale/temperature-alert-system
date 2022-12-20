package com.etraveli.tempalert.scheduler;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.etraveli.tempalert.datasource.UserCityCache;
import com.etraveli.tempalert.datasource.UserCityCache.UserCityData;
import com.etraveli.tempalert.feign.beans.TemperatureData;
import com.etraveli.tempalert.feign.client.TemperatureClient;
import com.etraveli.tempalert.processor.CityWiseEventProcessor;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureCollectSchedulerTest {

	private TemperatureCollectScheduler temperatureCollectScheduler;
	
	@Mock
	private TemperatureClient temperatureClient;
	
	@Mock
	private UserCityCache userCityCache;
	
	@Mock
	private CityWiseEventProcessor cityEventProcessor;
	
	@Before
	public void setUp() {
		temperatureCollectScheduler = new TemperatureCollectScheduler(temperatureClient, userCityCache, cityEventProcessor);
	}
	
	@Test
	public void testCollect_SingleCity() {
		List<TemperatureData> temperatureDatas = Arrays.asList(new TemperatureData("Mumbai", BigDecimal.valueOf(44.7), "celsius"),
				new TemperatureData("Delhi", BigDecimal.valueOf(15.1), "celsius"),
				new TemperatureData("bangalore", BigDecimal.valueOf(9.3), "celsius"));
		when(temperatureClient.getTemperature()).thenReturn(temperatureDatas);
		Map<String, LinkedList<UserCityData>> uMap = new HashMap<>();
		LinkedList<UserCityData> mumbaiDatas = new LinkedList<>();
		mumbaiDatas.add(new UserCityData(0, "Vikas", "Bhosale", "abc@gmail.com", "Mumbai", "gt", BigDecimal.valueOf(30.2)));
		mumbaiDatas.add(new UserCityData(0, "Anurag", "A", "efd@gmail.com", "Mumbai", "gt", BigDecimal.valueOf(50.2)));
		mumbaiDatas.add(new UserCityData(0, "Sushant", "K", "xyz@gmail.com", "Mumbai", "ls", BigDecimal.valueOf(10.2)));
		uMap.put("Mumbai", mumbaiDatas);
		when(userCityCache.getMap()).thenReturn(uMap);
		
		temperatureCollectScheduler.collect();
		
		verify(temperatureClient, only()).getTemperature();
		
		verify(cityEventProcessor, times(1)).process(Mockito.any(), Mockito.any());
	}
	
	@Test
	public void testCollect_MultipleCity() {
		List<TemperatureData> temperatureDatas = Arrays.asList(new TemperatureData("Mumbai", BigDecimal.valueOf(44.7), "celsius"),
				new TemperatureData("Delhi", BigDecimal.valueOf(15.1), "celsius"),
				new TemperatureData("bangalore", BigDecimal.valueOf(9.3), "celsius"));
		when(temperatureClient.getTemperature()).thenReturn(temperatureDatas);
		Map<String, LinkedList<UserCityData>> uMap = new HashMap<>();
		LinkedList<UserCityData> mumbaiDatas = new LinkedList<>();
		mumbaiDatas.add(new UserCityData(0, "Vikas", "Bhosale", "abc@gmail.com", "Mumbai", "gt", BigDecimal.valueOf(30.2)));
		mumbaiDatas.add(new UserCityData(0, "Anurag", "A", "efd@gmail.com", "Mumbai", "gt", BigDecimal.valueOf(50.2)));
		mumbaiDatas.add(new UserCityData(0, "Sushant", "K", "xyz@gmail.com", "Mumbai", "ls", BigDecimal.valueOf(10.2)));
		uMap.put("Mumbai", mumbaiDatas);
		LinkedList<UserCityData> delhiDatas = new LinkedList<>();
		delhiDatas.add(new UserCityData(0, "Vikas", "Bhosale", "abc@gmail.com", "Delhi", "gt", BigDecimal.valueOf(30.2)));
		uMap.put("Delhi", delhiDatas);
		when(userCityCache.getMap()).thenReturn(uMap);
		
		temperatureCollectScheduler.collect();
		
		verify(temperatureClient, only()).getTemperature();
		
		verify(cityEventProcessor, times(2)).process(Mockito.any(), Mockito.any());
	}
}
