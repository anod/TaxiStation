package com.station.taxi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 
 * @author alex
 */
@Aspect 
public class CabLoggingAspect {
	
    @Before("execution(* com.station.taxi.ICab.running(..))")
    public void logDriving(JoinPoint joinPoint) {

		System.out.println("logAfter() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");

    }
}
