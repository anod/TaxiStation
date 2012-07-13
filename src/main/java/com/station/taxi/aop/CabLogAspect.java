package com.station.taxi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
 
@Aspect 
public class CabLogAspect {
    
    @After("execution(* com.station.taxi.cab.driving(..))")
    public void logAfter(JoinPoint joinPoint) {
 
	System.out.println("logAfter() is running!");
	System.out.println("hijacked : " + joinPoint.getSignature().getName());
	System.out.println("******");
 
    }
}
