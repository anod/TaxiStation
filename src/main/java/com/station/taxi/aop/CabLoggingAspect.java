package com.station.taxi.aop;

import com.station.taxi.ICab;
import com.station.taxi.events.CabEventListener;
import com.station.taxi.logger.CabLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 
 * @author alex
 */
@Aspect 
public class CabLoggingAspect {
	private CabLogger mLogger;
	
	public void init() {
		mLogger = new CabLogger();
	}

	@Before("execution(* com.station.taxi.ICab.run(..))")
    public void logStart(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.START);
    }
	
	@AfterReturning("execution(* com.station.taxi.ICab.drive(..))")
	public void logDrive(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.DRIVE_DESTINATION);
	}
	
	@AfterReturning("execution(* com.station.taxi.ICab.goToWaiting(..))")
	public void logWaiting(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.WAITING);
	}

	@AfterReturning("execution(* com.station.taxi.ICab.goToBreak(..))")
	public void logBreak(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.GOTO_BREAK);
	}

	@AfterReturning("execution(* com.station.taxi.ICab.arrive(..))")
	public void logArrive(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.ARRIVED_DESTINATION);
	}

	@AfterReturning("execution(* com.station.taxi.ICab.interrupt(..))")
	public void logInterrupt(JoinPoint joinPoint) {
		update(joinPoint, CabEventListener.INTERRUPT);
	}

	private void update(JoinPoint joinPoint, int type) {
		ICab cab = (ICab)joinPoint.getTarget();				
		mLogger.update(type, cab);
	}
}
