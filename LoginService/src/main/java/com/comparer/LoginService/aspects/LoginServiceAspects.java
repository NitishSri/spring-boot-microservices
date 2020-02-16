package com.comparer.LoginService.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginServiceAspects {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before("execution(* com.comparer.LoginService.controller.LoginController.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("{} Method will execute with arguements {} ", joinPoint.getSignature().getName(),
				Arrays.toString(joinPoint.getArgs()));
	}

	@After("execution(* com.comparer.LoginService.controller.LoginController.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("{} Method will execute with arguements {} ", joinPoint.getSignature().getName(),
				Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(pointcut = "execution(* com.comparer.LoginService.controller.LoginController.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("{} Method executed with arguements {} and result {} ", joinPoint.getSignature().getName(),
				Arrays.toString(joinPoint.getArgs()), result);
	}

	@AfterThrowing(pointcut = "execution(* com.comparer.LoginService.controller.LoginController.*(..))", throwing = "error")
	public void afterThrowing(JoinPoint joinPoint, Object error) {
		logger.error("{} Method threw error with arguements {} with error {} ", joinPoint.getSignature().getName(),
				Arrays.toString(joinPoint.getArgs()), error);
	}
	
	@Around("execution(* com.comparer.LoginService.controller.LoginController.login(..))")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
		logger.info("Before invoking login method");
		Object value = null;
		try {
			value = joinPoint.proceed();
		} catch (Throwable e) {
			logger.info("Exception occured in login method {} ", value);
			e.printStackTrace();
		}
		logger.info("After invoking login method. Return value = {}",value);
		return value;
	}
	
}
