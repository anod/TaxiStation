package com.station.taxi.aop;

import com.station.taxi.events.CabEventListener;
import com.station.taxi.logger.CabLogger;
import com.station.taxi.model.Cab;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Logging AOP aspect for Cab actions
 * @author alex
 */
@Aspect 
public class CabLoggingAspect {
	private CabLogger mLogger;
	
	public void init() {
		mLogger = new CabLogger();
	}

	@Before("execution(* com.station.taxi.model.Cab.run(..))")
    public void logStart(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.START);
    }
	
	@AfterReturning("execution(* com.station.taxi.model.Cab.drive(..))")
	public void logDrive(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.DRIVE_DESTINATION);
	}
	
	@AfterReturning("execution(* com.station.taxi.model.Cab.goToWaiting(..))")
	public void logWaiting(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.WAITING);
	}

	@AfterReturning("execution(* com.station.taxi.model.Cab.goToBreak(..))")
	public void logBreak(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.GOTO_BREAK);
	}

	@Before("execution(* com.station.taxi.model.Cab.arrive(..))")
	public void logArrive(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.ARRIVED_DESTINATION);
	}

	@AfterReturning("execution(* com.station.taxi.model.Cab.interrupt(..))")
	public void logInterrupt(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.INTERRUPT);
	}

	/**
	 * Log an event
	 * @param joinPoint
	 * @param type 
	 */
	private void update(JoinPoint joinPoint, int type) {
		Cab cab = (Cab)joinPoint.getTarget();				
		mLogger.update(type, cab);
	}
}
