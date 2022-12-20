package com.etraveli.tempalert.feign.client;

import java.util.List;

import com.etraveli.tempalert.feign.beans.TemperatureData;

import feign.Headers;
import feign.RequestLine;

/**
 * Feign client for Temperature API.
 * 
 * @author VBhosale
 *
 */
public interface TemperatureClient {

	/**
	 * Get latest temperature.
	 * 
	 * @return List of temperature data for different region.
	 */
	@RequestLine("GET")
	@Headers("Content-Type: application/json")
	public List<TemperatureData> getTemperature();
}
