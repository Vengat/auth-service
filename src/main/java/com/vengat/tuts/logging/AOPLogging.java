package com.vengat.tuts.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class AOPLogging {
	private static final Logger log = LoggerFactory.getLogger(AOPLogging.class);

	@Pointcut(value = "execution(* com.vengat.tuts.controller.*.*(..) )")
	public void authServicePointCut() {
	}

	@Around("authServicePointCut()")
	public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().toString();
		Object[] methodParams = joinPoint.getArgs();
		log.info("Invoking method " + className + "." + methodName + "()" + " with parameters: "
				+ mapper.writeValueAsString(methodParams));
		Object response = joinPoint.proceed();
		log.info(className + "." + methodName + "()" + " Response : " + mapper.writeValueAsString(response));
		return response;
	}
}
