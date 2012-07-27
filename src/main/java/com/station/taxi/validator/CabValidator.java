package com.station.taxi.validator;

import org.springframework.util.StringUtils;
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
	public static final String ERR_CAB_NUM_CHARS = "err_addcab_num_chars";
	public static final String ERR_CAB_WAITING_EMPTY = "err_addcab_waiting_empty";
	
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
					errors.reject(ERR_CAB_NUM_LENGTH, String.format("Length of number must be between %d and %d chars", NUM_MIN_LEN, NUM_MAX_LEN));
					return;
				}

				for (char c: numberStr.toCharArray()){
					if(!Character.isDigit(c)){
						errors.reject(ERR_CAB_NUM_CHARS, "Only numbers allowed");
						return;
					}
				}
			}
		};
	}
	
	/**
	 * Validator for cab while waiting action
	 * @return 
	 */
	public static Validator getWhileWaitingValidator() {
		return new Validator() {

			@Override
			public boolean supports(Class<?> clazz) {
				return String.class.equals(clazz);
			}

			@Override
			public void validate(Object target, Errors errors) {
				String whileWaiting = (String)target;
				if (whileWaiting == null ||!StringUtils.hasText(whileWaiting.toString())) {
					errors.reject(ERR_CAB_WAITING_EMPTY, "While waiting string cannot be empty");
				}
			}
		};
	}
	
}
