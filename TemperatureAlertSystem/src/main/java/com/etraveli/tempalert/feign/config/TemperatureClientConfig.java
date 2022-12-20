package com.etraveli.tempalert.feign.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.etraveli.tempalert.feign.client.TemperatureClient;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

/**
 * Initialize and inject feign client bean.
 * 
 * @author VBhosale
 *
 */
@Configuration
public class TemperatureClientConfig {

	@Value("${temperature-system.url}")
	private String temperatureSystemURL;
	
	@Bean
	public TemperatureClient temperatureClient() {
		return Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new GsonEncoder())
				  .decoder(new GsonDecoder())
				  .logger(new Slf4jLogger(TemperatureClient.class))
				  .logLevel(Logger.Level.FULL)
				  .target(TemperatureClient.class, "http://localhost:8081/api/temperature");
	}
}
