/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.spring;

import com.station.taxi.ICab;
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
	
	public ICab createCab(int num, String whileWaiting) {
		Object o = mApplicationContext.getBean("cab", num, whileWaiting);
		return (ICab)o;
	}
	
}
