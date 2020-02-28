package com.example.RegisterationService.aspects;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegistrationControllerAspect {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around("controller() && allMethod()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			String className = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			Object result = joinPoint.proceed();
			long elapsedTime = System.currentTimeMillis() - start;
			logger.info("Method " + className + "." + methodName + " ()" + " execution time : " + elapsedTime + " ms");
			return result;
		} catch (IllegalArgumentException e) {
			logger.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
					+ joinPoint.getSignature().getName() + "()");
			throw e;
		}

	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controller() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void allMethod() {
	}

}
