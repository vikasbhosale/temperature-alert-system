package com.etraveli.tempalert.processor;

import java.util.LinkedList;

import com.etraveli.tempalert.feign.beans.TemperatureData;

/**
 * @author VBhosale
 *
 * @param <T>
 */
public interface EventProcessor<T> {

	/**
	 * @param temperatureData
	 * @param userSettings
	 */
	public void process(TemperatureData temperatureData, LinkedList<T> userSettings);
}
