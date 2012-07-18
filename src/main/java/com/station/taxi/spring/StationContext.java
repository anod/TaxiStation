/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.spring;

import com.station.taxi.Cab;
import com.station.taxi.ICab;
import com.station.taxi.IPassenger;
import com.station.taxi.Passenger;
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
	
	/**
	 * 
	 * @return 
	 */
	public ApplicationContext getApplicationContext() {
		return mApplicationContext;
	}
	
	/**
	 * 
	 * @param num
	 * @param whileWaiting
	 * @return 
	 */
	public ICab createCab(int num, String whileWaiting) {
		Advised advised = (Advised)mApplicationContext.getBean("cab", num, whileWaiting);
		try {
			Cab cab = (Cab) advised.getTargetSource().getTarget();
			cab.setAopProxy((ICab)advised);
		} catch (Exception ex) {}
		return (ICab)advised;
	}
	
	/**
	 * 
	 * @param name
	 * @param destination
	 * @return 
	 */
	public IPassenger createPassenger(String name, String destination) {
		Advised advised = (Advised)mApplicationContext.getBean("passenger", name, destination);
		try {
			Passenger p = (Passenger) advised.getTargetSource().getTarget();
			p.setAopProxy((IPassenger)advised);
		} catch (Exception ex) {}
		return (IPassenger)advised;
	}
	
}
