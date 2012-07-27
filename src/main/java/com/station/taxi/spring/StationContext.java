package com.station.taxi.spring;

import com.station.taxi.db.repositories.ReceiptRepository;
import com.station.taxi.model.Cab;
import com.station.taxi.model.Passenger;
import com.station.taxi.model.TaxiCab;
import com.station.taxi.model.TaxiPassenger;
import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 *
 * @author alex
 */
abstract public class StationContext {
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
	public Cab createCab(int num, String whileWaiting) {
		Object object = mApplicationContext.getBean("cab", num, whileWaiting);
		Advised advised = (Advised)object;
		try {
			TaxiCab cab = (TaxiCab) advised.getTargetSource().getTarget();
			cab.setAopProxy((Cab)advised);
		} catch (Exception ex) {}
		return (Cab)advised;
	}
	
	/**
	 * 
	 * @param name
	 * @param destination
	 * @return 
	 */
	public Passenger createPassenger(String name, String destination) {
		Advised advised = (Advised)mApplicationContext.getBean("passenger", name, destination);
		try {
			TaxiPassenger p = (TaxiPassenger) advised.getTargetSource().getTarget();
			p.setAopProxy((Passenger)advised);
		} catch (Exception ex) {}
		return (Passenger)advised;
	}
	
	/**
	 * Get receipt data access object
	 * @return 
	 */
	public ReceiptRepository getReceiptRepository() {

		ReceiptRepository repository = mApplicationContext.getBean(ReceiptRepository.class);

		return repository;
	}
	
}
