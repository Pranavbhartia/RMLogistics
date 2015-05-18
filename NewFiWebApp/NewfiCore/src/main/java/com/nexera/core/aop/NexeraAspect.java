package com.nexera.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class NexeraAspect {

	private static final Logger LOG = LoggerFactory
	        .getLogger(NexeraAspect.class);

	private static final Object NEWLINE = "\n";

	@Before("execution(* com.nexera.core..*(..)) ")
	public void logBefore(JoinPoint joinPoint) {

		LOG.info("Method invoked : " + joinPoint.getSignature());
		StringBuilder arguments = new StringBuilder();
		Object[] args = joinPoint.getArgs();
		for (Object object : args) {
			arguments.append(object).append(NEWLINE);
		}
		LOG.info("Method parameters : " + arguments);

	}
}
