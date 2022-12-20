package com.etraveli.tempalert.datasource;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.etraveli.tempalert.model.User;

/**
 * @author VBhosale
 *
 */
@Component
public class StaticDB {

	private Random rn = new Random();
	
	private Map<Long, User> userData = new ConcurrentHashMap<>();
	
	public User getUser(long id) {
		return userData.get(id);
	}
	
	/**
	 * @return
	 */
	public List<User> getAllUser() {
		return userData.entrySet().stream().map(Entry<Long, User>::getValue).collect(Collectors.toList());
	}
	
	/**
	 * @param user
	 * @return
	 */
	public User createUser(User user) {
		user.setId(rn.nextInt(10));
		userData.put(user.getId(), user);
		return user;
	}
	
	/**
	 * @param user
	 * @return
	 */
	public User updateUser(User user) {
		userData.put(user.getId(), user);
		return user;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public User deleteUser(long id) {
		User user = userData.get(id);
		userData.remove(id);
		return user;
	}
}
