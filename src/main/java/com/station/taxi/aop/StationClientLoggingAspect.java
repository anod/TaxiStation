/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.aop;

import com.station.taxi.logger.LoggerWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 *
 * @author alex
 */
@Aspect
public class StationClientLoggingAspect {

	@Before("execution(* com.station.taxi.sockets.Client.connect(..))")
    public void logBeforeConnect(JoinPoint joinPoint) {
		LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Connecting...");
    }

	@AfterReturning(pointcut="execution(* com.station.taxi.sockets.Client.connect(..))", returning = "result")
    public void logAfterConnect(JoinPoint joinPoint, Object result) {
		boolean isConnected = (Boolean)result;
		if (isConnected) {
			LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Succesfully connected to server");
		} else {
			LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Failed to connect to server");			
		}
    }
	
	@Before("execution(* com.station.taxi.sockets.Client.close(..))")
    public void logBeforeClose(JoinPoint joinPoint) {
		LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Closing connection...");
    }

	@Before("execution(* com.station.taxi.sockets.Client.communicate(..))")
    public void logBeforeCommunicate(JoinPoint joinPoint) {
		LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Starting communication with server...");
    }

}