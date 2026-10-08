package kz.iitu.spring_lab_01.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(2)
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Срабатывает ДО вызова метода
    @Before("kz.iitu.spring_lab_01.aspect.Pointcuts.serviceOperation()")
    public void before(JoinPoint jp) {
        log.info("[LOG] -> {} args={}",
                jp.getSignature().toShortString(),
                Arrays.toString(jp.getArgs()));
    }

    // Срабатывает ПОСЛЕ успешного выполнения метода
    @AfterReturning(pointcut = "kz.iitu.spring_lab_01.aspect.Pointcuts.serviceOperation()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        log.info("[LOG] <- {} returned {}",
                jp.getSignature().toShortString(),
                result);
    }

    // Срабатывает, если метод выбросил ИСКЛЮЧЕНИЕ
    @AfterThrowing(pointcut = "kz.iitu.spring_lab_01.aspect.Pointcuts.serviceOperation()", throwing = "ex")
    public void afterThrowing(JoinPoint jp, Throwable ex) {
        log.error("[LOG] X {} threw {}: {}",
                jp.getSignature().toShortString(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }
}