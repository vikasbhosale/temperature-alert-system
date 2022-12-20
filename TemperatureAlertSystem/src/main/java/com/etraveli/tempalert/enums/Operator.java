package com.etraveli.tempalert.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author VBhosale
 *
 */
public enum Operator {

	GT("GT", "greater than"),
	LS("LS", "less than"),
	EQ("EQ", "equals");

	private String value;
	private String desciption;
	
	Operator(String value, String desciption) {
		this.value = value;
		this.desciption = desciption;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return
	 */
	public String getDesciption() {
		return desciption;
	}
	
	public static Operator findByValue(String value) {
	    return Arrays
	        .stream(values()).filter(currentValue -> Optional.of(currentValue)
	            .map(Operator::getValue).filter(respCode -> respCode.equals(value)).isPresent())
	        .findFirst().orElse(null);
	  }
	
}
