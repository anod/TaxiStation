/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Container for Spring Validators for Cab 
 * @author alex
 */
public class CabValidator {
	private static final int NUM_MIN_LEN = 3;
	private static final int NUM_MAX_LEN = 5;

	public static final String ERR_CAB_NUM_LENGTH = "err_addcab_num_length";
	public static final String ERR_CAB_NUM_CHARS = "dialog_addcab_num_err_chars";
	
	/**
	 * Validator for cab number string
	 * @return 
	 */
	public static Validator getNumberStringValidator() {
		return new Validator() {
			
			@Override
			public boolean supports(Class<?> clazz) {
				return String.class.equals(clazz);
			}

			@Override
			public void validate(Object target, Errors errors) {
				String numberStr = (String)target;
				if (numberStr.length() < NUM_MIN_LEN || numberStr.length() > NUM_MAX_LEN) {
					errors.reject(ERR_CAB_NUM_LENGTH);
					return;
				}

				for (char c: numberStr.toCharArray()){
					if(!Character.isDigit(c)){
						errors.reject(ERR_CAB_NUM_CHARS);
						return;
					}
				}
			}
		};
	}
	
	
	
}
