package com.etraveli.tempalert.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.etraveli.tempalert.datasource.StaticDB;
import com.etraveli.tempalert.model.TemperatureSetting;
import com.etraveli.tempalert.model.User;
import com.google.gson.Gson;

/**
 * @author VBhosale
 *
 */
public class UserManagementControllerTest {

	private MockMvc mockMvc;
	
	private StaticDB staticDB;
	
	private Gson gson = new Gson();
	
	@Before
	public void setUp() {
		staticDB = new StaticDB();
		mockMvc = MockMvcBuilders.standaloneSetup(new UserManagementController(staticDB, Collections.emptyList())).build();
	}
	
	/**
	 * <b>Given</b> Prepare user object <br>
	 * <b>when</b> call create user endpoint  <br>
	 * <b>Then</b> User created in static DB with new Id <br>
	 */
	@Test
	public void testCreateUser() throws Exception {
		User user = User.builder()
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
		mockMvc.perform(MockMvcRequestBuilders.post(UserRestURIConstants.CREATE_USER)
				.content(gson.toJson(user))
		.contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id")
        .exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName")
        .value("Vikas"));
	}
	
	/**
	 * <b>Given</b> Modify last name of stored user object <br>
	 * <b>when</b> call update user endpoint  <br>
	 * <b>Then</b> User updated with last name <br>
	 */
	@Test
	public void testUpdateUser() throws Exception {
		User user = User.builder()
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
		User createUser = staticDB.createUser(user);
		createUser.setLastName("B");
		User updatedUser = createUser;
		mockMvc.perform(MockMvcRequestBuilders.post(UserRestURIConstants.UPDATE_USER)
				.content(gson.toJson(updatedUser))
		.contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id")
        .exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName")
        .value("B"));
	}
	
	/**
	 * <b>Given</b> Get id of stored user which need to be deleted <br>
	 * <b>when</b> call delete user endpoint  <br>
	 * <b>Then</b> User deleted <br>
	 */
	@Test
	public void testDeleteUser() throws Exception {
		User user = User.builder()
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
		User createUser = staticDB.createUser(user);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(UserRestURIConstants.DELETE_USER, createUser.getId())
//				.param("id", "123")
		.contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/**
	 * <b>Given</b> Add few users in static DB <br>
	 * <b>when</b> call get user endpoint with one of userid  <br>
	 * <b>Then</b> User get retrieved <br>
	 */
	@Test
	public void testGetUser() throws Exception {
		User user = User.builder()
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
		User createUser = staticDB.createUser(user);
		
		mockMvc.perform(MockMvcRequestBuilders.get(UserRestURIConstants.GET_USER, createUser.getId())
		.contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id")
        .exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName")
        .value("Bhosale"));
	}
	
	/**
	 * <b>Given</b> Add few users in static DB <br>
	 * <b>when</b> call get all user endpoint <br>
	 * <b>Then</b> All User get retrieved <br>
	 */
	@Test
	public void testGetAllUser() throws Exception {
		User user = User.builder()
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
		staticDB.createUser(user);
		
		mockMvc.perform(MockMvcRequestBuilders.get(UserRestURIConstants.GET_ALL_USER)
		.contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$..id")
        .exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$..lastName")
        .value("Bhosale"));
	}
}
