/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.spring;

import com.station.taxi.Cab;
import com.station.taxi.ICab;
import org.springframework.aop.framework.Advised;
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
		Advised advised = (Advised)mApplicationContext.getBean("cab", num, whileWaiting);
		try {
			Cab cab = (Cab) advised.getTargetSource().getTarget();
			cab.setAopProxy((ICab)advised);
		} catch (Exception ex) {}
		return (ICab)advised;
	}
	
}
