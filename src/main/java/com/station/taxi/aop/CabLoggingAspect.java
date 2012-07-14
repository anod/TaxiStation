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

	@Before("execution(* com.station.taxi.ICab.run(..))")
    public void logStart(JoinPoint joinPoint) {
		ICab cab = (ICab)joinPoint.getThis();
				
		CabLogger logger = new CabLogger();
		logger.update(CabEventListener.START, cab);
		System.out.println("logRunning() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");

    }
}
