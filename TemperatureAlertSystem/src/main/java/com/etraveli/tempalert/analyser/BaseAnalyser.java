package com.etraveli.tempalert.analyser;

import com.etraveli.tempalert.model.User;

/**
 * The {@code BaseAnalyser} interface should be implemented by any
 * class whose instances are intended to be analyze by different parameters
 * like City, longitude and latitude etc.
 * 
 * @author VBhosale
 *
 */
public interface BaseAnalyser {

	/**
	 * When an object implementing interface {@code BaseAnalyser} is used
     * to create a Analyze and process the User and User temperature settings,
     * {@code process} method to be called in that separately executing
     * thread.
     * 
	 * @param user
	 * @param action
	 */
	void process(User user, String action);
}
