package com.aliang.wenda.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

//    @Before("execution(* com.aliang.wenda.controller.*.*(..))")
//    public void beforeMethod(JoinPoint joinPoint){
//        logger.info("before Method");
//    }
//
//    @After("execution(* com.aliang.wenda.controller.*.*(..))")
//    public void afterMethod(){
//        logger.info("after method");
//    }
}
