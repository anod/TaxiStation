/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.spring;

import com.station.taxi.Cab;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author alex
 */
public class StationContext {
	private final ApplicationContext mApplicationContext;
	
	public StationContext(ApplicationContext applicationContext) {
		mApplicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return mApplicationContext;
	}
	
	public Cab createCab(int num, String whileWaiting) {
		return (Cab)mApplicationContext.getBean("cab", num, whileWaiting);
	}
	
}
