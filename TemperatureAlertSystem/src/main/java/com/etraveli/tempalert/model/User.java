package com.etraveli.tempalert.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author VBhosale
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private List<TemperatureSetting> temperatureSettings;
	
}
