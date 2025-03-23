package com.centime.project.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    @Before("@annotation(LogMethodParam)")
    public void logMethodParams(JoinPoint joinPoint) {
        System.out.println("Executing: " + joinPoint.getSignature());
        System.out.println("Parameters: " + Arrays.toString(joinPoint.getArgs()));
    }
}

