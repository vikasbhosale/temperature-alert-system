package com.etraveli.tempalert.enums;

/**
 * @author VBhosale
 *
 */
public enum Operator {

	GT("GT", "Greater than"),
	LS("LS", "Less than"),
	EQ("EQ", "Equals");

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
	
	
	
}
