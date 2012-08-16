package com.station.taxi.aop;

import com.station.taxi.logger.LoggerWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.simple.JSONObject;

/**
 * Socket JSON Client logging aspect
 * @author alex
 */
@Aspect
public class JSONClientLoggingAspect {

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

	@Before("execution(* com.station.taxi.sockets.Client.sendRequest(..))")
    public void logBeforeSendRequest(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Sending request: " + ((JSONObject)args[0]).toString());
    }

	@AfterReturning(pointcut="execution(* com.station.taxi.sockets.Client.receiveResponse(..))", returning = "result")
    public void logAfterReceiveResponse(JoinPoint joinPoint, Object result) {
		JSONObject json = (JSONObject)result;
		if (json != null) {
			LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Recieved response: " + json.toString());
		} else {
			LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Empty response.");			
		}
    }
	
	@Before("execution(* com.station.taxi.sockets.Client.sendAndReceive(..))")
    public void logBeforeSendAndReceive(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Sending request: " + ((JSONObject)args[0]).toString());
    }

	@AfterReturning(pointcut="execution(* com.station.taxi.sockets.Client.sendAndReceive(..))", returning = "result")
    public void logAfterSendAndReceive(JoinPoint joinPoint, Object result) {
		JSONObject json = (JSONObject)result;
		if (json != null) {
			LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Recieved response: " + json.toString());
		} else {
			LoggerWrapper.log(joinPoint.getTarget().getClass().getSimpleName(),"Empty response.");			
		}
    }

}
