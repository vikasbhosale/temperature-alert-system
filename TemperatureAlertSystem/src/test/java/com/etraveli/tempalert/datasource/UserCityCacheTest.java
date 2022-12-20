package com.etraveli.tempalert.datasource;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.etraveli.tempalert.datasource.UserCityCache.UserCityData;
import com.etraveli.tempalert.model.TemperatureSetting;
import com.etraveli.tempalert.model.User;

/**
 * @author VBhosale
 *
 */
public class UserCityCacheTest {

	/**
	 * <b>Test Method</b> - userCityCache.put(user) <br><br>
	 * <b>Given</b> - New User with new cities <br>
	 * <b>when</b> put method called <br>
	 * <b>Then</b> {@link UserCityCache} build with two entries in Map. <br>
	 */
	@Test
	public void testput_NewCity() {
		UserCityCache userCityCache = new UserCityCache();
		User user = User.builder()
		.id(123)
		.firstName("Vikas")
		.lastName("Bhosale")
		.temperatureSettings(Arrays.asList(
				TemperatureSetting.builder()
					.id(111)
					.city("Mumbai")
					.condition("ls")
					.reading(BigDecimal.valueOf(30))
				.build(),
				TemperatureSetting.builder()
					.id(111)
					.city("Navi Mumbai")
					.condition("gt")
					.reading(BigDecimal.valueOf(20))
				.build()
				))
		.build();
		userCityCache.put(user);
		assertEquals(2, userCityCache.getMap().size());
		assertEquals(1, userCityCache.get("Mumbai").size());
		assertEquals(1, userCityCache.get("Navi Mumbai").size());
	}
	
	/**
	 * <b>Test Method</b> - userCityCache.put(user) <br><br>
	 * <b>Given</b> - New User with no temperature settings <br>
	 * <b>when</b> put method called <br>
	 * <b>Then</b> {@link UserCityCache} build nothing in the Map. <br>
	 */
	@Test
	public void testput_NoSettings() {
		UserCityCache userCityCache = new UserCityCache();
		User user = User.builder()
		.id(123)
		.firstName("Vikas")
		.lastName("Bhosale")
		.temperatureSettings(Arrays.asList())
		.build();
		userCityCache.put(user);
		assertEquals(0, userCityCache.getMap().size());
	}
	
	/**
	 * <b>Test Method</b> - userCityCache.get(<{@code cityName}>); <br><br>
	 * <b>Given</b> - 2 users set the temperature alerts with 3 different cities <br>
	 * <b>AND</b> {@link UserCityCache} build with 3 entries of Map. <br>
	 * <b>when</b> get method called on Mumbai city" <br>
	 * <b>Then</b> {@link UserCityCache} return 2 {@code UserCityData} objects. <br>
	 */
	@Test
	public void testGet() {
		UserCityCache userCityCache = new UserCityCache();
		User user = User.builder()
		.id(123)
		.firstName("Vikas")
		.lastName("Bhosale")
		.temperatureSettings(Arrays.asList(
				TemperatureSetting.builder()
					.id(111)
					.city("Mumbai")
					.condition("ls")
					.reading(BigDecimal.valueOf(30))
				.build(),
				TemperatureSetting.builder()
					.id(111)
					.city("Navi Mumbai")
					.condition("gt")
					.reading(BigDecimal.valueOf(20))
				.build()
				))
		.build();
		userCityCache.put(user);
		
		assertEquals(2, userCityCache.getMap().size());
		
		User user1 = User.builder()
		.id(123)
		.firstName("Anurag")
		.lastName("A")
		.temperatureSettings(Arrays.asList(
				TemperatureSetting.builder()
					.id(111)
					.city("Mumbai")
					.condition("ls")
					.reading(BigDecimal.valueOf(30))
				.build(),
				TemperatureSetting.builder()
					.id(111)
					.city("Delhi")
					.condition("gt")
					.reading(BigDecimal.valueOf(20))
				.build()
				))
		.build();
		userCityCache.put(user1);
		
		assertEquals(3, userCityCache.getMap().size());
		
		List<UserCityData> userCityDatas = userCityCache.get("Mumbai");
		assertEquals(2, userCityDatas.size());
		
		List<UserCityData> userCityDatas1 = userCityCache.get("Delhi");
		assertEquals(1, userCityDatas1.size());
		
		List<UserCityData> userCityDatas2 = userCityCache.get("Navi Mumbai");
		assertEquals(1, userCityDatas2.size());
	}
	
	/**
	 * <b>Test Method</b> - userCityCache.put(user) <br><br>
	 * <b>Given</b> - New User with new cities <br>
	 * <b>when</b> put method called <br>
	 * <b>Then</b> {@link UserCityCache} build with two entries in Map. <br>
	 */
	@Test
	public void testremove_User() {
		UserCityCache userCityCache = new UserCityCache();
		User user = User.builder()
		.id(123)
		.firstName("Vikas")
		.lastName("Bhosale")
		.temperatureSettings(Arrays.asList(
				TemperatureSetting.builder()
					.id(111)
					.city("Mumbai")
					.condition("ls")
					.reading(BigDecimal.valueOf(30))
				.build(),
				TemperatureSetting.builder()
					.id(111)
					.city("Navi Mumbai")
					.condition("gt")
					.reading(BigDecimal.valueOf(20))
				.build()
				))
		.build();
		userCityCache.put(user);
		assertEquals(2, userCityCache.getMap().size());
		assertEquals(1, userCityCache.get("Mumbai").size());
		assertEquals(1, userCityCache.get("Navi Mumbai").size());
		
		userCityCache.remove(user);
		assertEquals(2, userCityCache.getMap().size());
		assertEquals(0, userCityCache.get("Mumbai").size());
		assertEquals(0, userCityCache.get("Navi Mumbai").size());
	}
}
