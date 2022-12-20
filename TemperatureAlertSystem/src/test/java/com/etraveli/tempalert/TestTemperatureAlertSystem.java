package com.etraveli.tempalert;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.etraveli.tempalert.controller.UserRestURIConstants;
import com.etraveli.tempalert.model.User;

public class TestTemperatureAlertSystem {

	public static final String SERVER_URI = "http://localhost:8080/TusereratureAlertSystem";
	
	public static void main(String args[]){
		
		System.out.println("*****");
		testCreateUser();
		System.out.println("*****");
		testGetUser();
		System.out.println("*****");
		testGetAllUser();
	}

	private static void testGetAllUser() {
		RestTemplate restTuserlate = new RestTemplate();
		//we can't get List<User> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		List<LinkedHashMap> users = restTuserlate.getForObject(SERVER_URI + UserRestURIConstants.GET_ALL_USER, List.class);
		System.out.println(users.size());
		for(LinkedHashMap map : users){
			System.out.println("ID="+map.get("id")+",Name="+map.get("name")+",CreatedDate="+map.get("createdDate"));;
		}
	}

	private static void testCreateUser() {
		RestTemplate restTuserlate = new RestTemplate();
		User user = new User();
		user.setId(1);
		User response = restTuserlate.postForObject(SERVER_URI + UserRestURIConstants.CREATE_USER, user, User.class);
		printUserData(response);
	}

	private static void testGetUser() {
		RestTemplate restTuserlate = new RestTemplate();
		User user = restTuserlate.getForObject(SERVER_URI + "/rest/user/1", User.class);
		printUserData(user);
	}
	
	public static void printUserData(User user){
		System.out.println(user);
	}
}
