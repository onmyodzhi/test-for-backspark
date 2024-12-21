package com.sidorov.backspark.aspects.socks.service;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class SocksServiceAspect {

    @Before("execution(* com.sidorov.backspark.socks.services.SockService.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        logMethodDetails(joinPoint, "Calling method: {} with arguments: {}");
    }

    @After("execution(* com.sidorov.backspark.socks.services.SockService.*(..))")
    public void logMethodResult(JoinPoint joinPoint) {
        logMethodDetails(joinPoint, "Method {} executed successfully.");
    }

    @AfterThrowing(pointcut = "execution(* com.sidorov.backspark.socks.services.SockService.*(..))", throwing = "exception")
    public void logMethodError(JoinPoint joinPoint, Throwable exception) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        log.error("Method {} threw an exception: ", methodName, exception);
    }

    @Around("execution(* com.sidorov.backspark.socks.services.SockService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        log.info("Method {} executed in {} ms.", methodName, stopWatch.getTotalTimeMillis());

        return result;
    }

    private void logMethodDetails(JoinPoint joinPoint, String message) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        log.info(message, methodName, joinPoint.getArgs());
    }
}
