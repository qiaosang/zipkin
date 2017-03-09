package com.lesports.albatross.commons.trace;

import com.lesports.albatross.commons.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
@Slf4j
public class TracingAspect {

    private boolean printStackTrace = false;

    public TracingAspect(boolean printStackTrace) {
        this.printStackTrace = printStackTrace;
    }

    @AfterThrowing(pointcut = "@annotation(com.lesports.albatross.commons.trace.TracingPoint)", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        if (printStackTrace) {
            logger.error("Exception in .{}() with cause = {}",
                    joinPoint.getSignature().getName(), e.getCause(), e);
        } else {
            logger.error("Exception in .{}() with cause = {}",
                    joinPoint.getSignature().getName(), e.getCause());
        }
    }

    @Around("@annotation(com.lesports.albatross.commons.trace.TracingPoint)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        TracingPoint metadata = ReflectionUtils.getAnnotation(joinPoint, TracingPoint.class);
        String logMessage = String.format("%s Enter: .%s(args: %s) - message: %s", metadata.marker(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), metadata.message());
        logging(logger, metadata.level(), logMessage);
        try {
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logMessage = String.format("%s Exit: .%s() with result = %s ... consuming: %d ms", metadata.marker(),
                    joinPoint.getSignature().getName(), result == null ? "" : result.toString(), endTime - startTime);
            logging(logger, metadata.level(), logMessage);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

    private void logging(Logger logger, TracingPoint.LogLevel logLevel, String logMessage) {
        switch (logLevel) {
            case DEBUG:
                logger.debug(logMessage);
                break;
            case INFO:
                logger.info(logMessage);
                break;
            case WARN:
                logger.warn(logMessage);
                break;
            case ERROR:
            case FATAL:
                logger.error(logMessage);
                break;
            case TRACE:
            default:
                logger.trace(logMessage);
                break;
        }
    }
}
