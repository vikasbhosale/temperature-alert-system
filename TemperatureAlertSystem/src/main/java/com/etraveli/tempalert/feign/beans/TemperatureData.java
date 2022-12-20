package com.etraveli.tempalert.feign.beans;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author VBhosale
 *
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class TemperatureData {

	private String city;
	private BigDecimal reading;
	private String Unit;
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
//	private LocalDateTime readingDateTime;
}
