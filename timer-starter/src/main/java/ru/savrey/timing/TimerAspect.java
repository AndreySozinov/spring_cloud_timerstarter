package ru.savrey.timing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.savrey.TimingProperties;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TimerAspect {

    private final TimingProperties properties;

    @Pointcut("within(ru.savrey.spring_cloud.*)")
    public void classAnnotatedWith() {

    }

    @Pointcut("@annotation(ru.savrey.timing.Timer)")
    public void methodsAnnotatedWith() {

    }

    @Around("classAnnotatedWith() || methodsAnnotatedWith()")
    public Object timerAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        if (properties.isTiming()) {
            try {
                long startTime = System.currentTimeMillis();
                Object result = joinPoint.proceed();
                long elapsedTime = System.currentTimeMillis() - startTime;

                log.info("Class: " + joinPoint.getTarget().getClass().getName() +
                        " Method: " + joinPoint.getSignature().getName() +
                        " Elapsed Time: " + elapsedTime + " ms.");

                return result;
            } catch (Throwable ex) {
                log.error("exception = [{}, {}]", ex.getClass(), ex.getMessage());
                throw ex;
            }
        } else {
            try {
                return joinPoint.proceed();
            } catch (Throwable ex) {
                log.error("exception = [{}, {}]", ex.getClass(), ex.getMessage());
                throw ex;
            }
        }
    }
}
