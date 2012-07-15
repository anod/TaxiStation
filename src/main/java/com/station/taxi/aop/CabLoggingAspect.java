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
		System.out.println("CabLoggingAspect: init()");
		mLogger = new CabLogger();
	}
	
	@Pointcut("execution(* com.station.taxi.ICab.*(..))")
	public void anyCall() { }
	
	@Before("anyCall()")
	public void logAnyCall(JoinPoint joinPoint) {
		System.out.println("CabLoggingAspect: logAnyCall(): "+joinPoint.getSignature().getName());
	}
	
	@Before("execution(* com.station.taxi.ICab.run(..))")
    public void logStart(JoinPoint joinPoint) {
		System.out.println("CabLoggingAspect: logStart()");

		ICab cab = (ICab)joinPoint.getTarget();		
		mLogger.update(CabEventListener.START, cab);
    }
	
	@AfterReturning("execution(* com.station.taxi.ICab.drive(..))")
	public void logDrive(JoinPoint joinPoint) {
		System.out.println("CabLoggingAspect: logDrive()");

		ICab cab = (ICab)joinPoint.getTarget();				
		mLogger.update(CabEventListener.DRIVE_DESTINATION, cab);
	}
	
	@AfterReturning("execution(* com.station.taxi.ICab.goToWaiting(..))")
	public void logWaiting(JoinPoint joinPoint) {
		System.out.println("CabLoggingAspect: logWaiting()");

		ICab cab = (ICab)joinPoint.getTarget();				
		mLogger.update(CabEventListener.WAITING, cab);
	}

	@AfterReturning("execution(* com.station.taxi.ICab.goToBreak(..))")
	public void logBreak(JoinPoint joinPoint) {
		System.out.println("CabLoggingAspect: logBreak()");

		ICab cab = (ICab)joinPoint.getTarget();				
		mLogger.update(CabEventListener.GOTO_BREAK, cab);
	}

	@AfterReturning("execution(* com.station.taxi.ICab.arrive(..))")
	public void logArrive(JoinPoint joinPoint) {
		System.out.println("CabLoggingAspect: logArrive()");

		ICab cab = (ICab)joinPoint.getTarget();				
		mLogger.update(CabEventListener.ARRIVED_DESTINATION, cab);
	}

}
