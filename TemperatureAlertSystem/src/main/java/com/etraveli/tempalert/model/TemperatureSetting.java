package com.etraveli.tempalert.model;

import java.math.BigDecimal;

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
public class TemperatureSetting {
	private long id;
	private String city;
	private String condition;
	private BigDecimal reading;
}
