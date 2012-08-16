package com.station.taxi.aop;

import com.station.taxi.logger.LoggerWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.simple.JSONObject;

/**
 * AOP Aspect to log Socket JSON worker actions 
 * @author alex
 */
@Aspect
public class JSONWorkerLoggingAspect {
	
	@Before("execution(* com.station.taxi.sockets.Worker.sendResponse(..))")
    public void logBeforeSendResponse(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Sending response: " + ((JSONObject)args[0]).toString());
    }

	@AfterReturning(pointcut="execution(* com.station.taxi.sockets.Worker.readRequest(..))", returning = "result")
    public void logAfterReadRequest(JoinPoint joinPoint, Object result) {
		JSONObject json = (JSONObject)result;
		if (json != null) {
			LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Recieved request: " + json.toString());
		} else {
			LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Empty request.");			
		}
    }
}
