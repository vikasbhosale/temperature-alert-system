package com.etraveli.tempalert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.etraveli.tempalert.analyser.BaseAnalyser;
import com.etraveli.tempalert.datasource.StaticDB;
import com.etraveli.tempalert.exception.handler.BadRequestExecption;
import com.etraveli.tempalert.exception.handler.BadRequestExecption.ErrorCodeEnum;
import com.etraveli.tempalert.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the User management service.
 * 
 * @author VBhosale
 *
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserManagementController {
	
	private final StaticDB staticDB;
	
	private final List<BaseAnalyser> dataAnalyser;
	
	@GetMapping(UserRestURIConstants.GET_USER)
	public User getUser(@PathVariable("id") long userId) {
		log.info("Start getUser. ID="+userId);
		return staticDB.getUser(userId);
	}
	
	@GetMapping(UserRestURIConstants.GET_ALL_USER)
	public List<User> getAllUsers() {
		log.info("Start getAllUsers.");
		return staticDB.getAllUser();
	}
	
	@PostMapping(UserRestURIConstants.CREATE_USER)
	public User createUser(@RequestBody User user) {
		log.info("Start createUser.");
		if (staticDB.isEmailIdExist(user.getEmail())) {
			log.error("User with Email id already exist Failure");
			throw new BadRequestExecption(ErrorCodeEnum.ER101);
		}
		User createUser = staticDB.createUser(user);
		analyseData(createUser, UserRestURIConstants.CREATE_USER);
		return createUser;
	}
	
	@PostMapping(UserRestURIConstants.UPDATE_USER)
	public User updateUser(@RequestBody User user) {
		log.info("Start updateUser.");
		if (user.getId() == 0) {
			log.error("Id cannot be Zero or null");
			throw new BadRequestExecption(ErrorCodeEnum.ER103);
		}
		if (staticDB.getUser(user.getId()) == null) {
			log.error("No user exist with ID");
			throw new BadRequestExecption(ErrorCodeEnum.ER104);
		}
		User updatedUser = staticDB.updateUser(user);
		analyseData(updatedUser, UserRestURIConstants.UPDATE_USER);
		return updatedUser;
	}
	
	@DeleteMapping(UserRestURIConstants.DELETE_USER)
	public User deleteUser(@PathVariable("id") long userId) {
		log.info("Start deleteUser.");
		User user = staticDB.getUser(userId);
		analyseData(user, UserRestURIConstants.DELETE_USER);
		return staticDB.deleteUser(userId);
	}
	
	/**
	 * Call background jobs for user.
	 *
	 * @param userName
	 */
	private void analyseData(User user, String action) {
		dataAnalyser.forEach(anlzr -> anlzr.process(user, action));
	}
}
