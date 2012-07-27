package com.station.taxi.aop;

import com.station.taxi.logger.LoggerWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 *
 * @author Alex Gavrishev
 */
@Aspect
public class StationServerLoggingAspect {
	
	@Before("execution(* com.station.taxi.sockets.Server.start(..))")
    public void logStart(JoinPoint joinPoint) {
		LoggerWrapper.log("StationServer :: start");
    }

	@Before("execution(* com.station.taxi.sockets.Server.accept(..))")
    public void logAccept(JoinPoint joinPoint) {
		LoggerWrapper.log("StationServer :: accepting connections....");
    }
	
	@Before("execution(* com.station.taxi.sockets.Server.stop(..))")
    public void logStop(JoinPoint joinPoint) {
		LoggerWrapper.log("StationServer :: stop");
    }
}
