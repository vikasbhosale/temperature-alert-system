package com.etraveli.tempalert.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * External properties file.
 * 
 * @author VBhosale
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesConfiguration {

}
