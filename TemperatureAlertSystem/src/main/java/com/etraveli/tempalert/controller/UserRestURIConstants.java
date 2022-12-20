package com.etraveli.tempalert.controller;

import lombok.extern.slf4j.Slf4j;

/**
 * @author VBhosale
 *
 */
@Slf4j
public class UserRestURIConstants {

	private UserRestURIConstants() {
		log.info("Utility class");
	}
	
	public static final String GET_USER = "/rest/user/{id}";
	public static final String GET_ALL_USER = "/rest/users";
	public static final String CREATE_USER = "/rest/user/create";
	public static final String UPDATE_USER = "/rest/user/update";
	public static final String DELETE_USER = "/rest/user/delete/{id}";
}
