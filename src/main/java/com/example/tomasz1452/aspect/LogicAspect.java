package com.example.tomasz1452.aspect;


import com.example.tomasz1452.model.ProjectService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Ten aspect tutaj ma chyba za zadanie jakby otoczyć jedną metode i aprawdzić jej działanie
 * w aspekcie czasu wykonywania się podanej metody.
 * Jest to jakby nasza włąsna metryka którą można wywołac za pomoca adresu
 *      http://localhost:8080/status/metrics/model.project.create.group
 *
 * Nie za bardo mi to chyba działa bo wyniki po wywołaniu tego z przegłądarce
 * wyświetalają sie 0.0. Nie wime czy to tak ma być.
 */
@Aspect
@Component
class LogicAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogicAspect.class);
    private final Timer projectCreateGroupTimer;

    LogicAspect(final MeterRegistry registry){
        projectCreateGroupTimer = registry.timer("model.project.create.group");
    }

    @Pointcut("execution(* com.example.tomasz1452.model.ProjectService.createGroup(..))")
    static void projectServiceCreateGroup(){

    }

        /*
        Znaczek * na początku oznacza dowolną metode z podanej lokalizacji.
        W tym przypadku doprecyzowaliśmy że chcemy metodę createGroup()
        Dwie .. kropki znaczają że pobieramy wersję metody z dwoma parametrami.
        Aby jeszcze bardziej doprecyzować można dodać np. zapis && @annotation(@overrride)
        w takim przypadku dodamy że chodzi nam o metode która posiada konkretną adnotację.
        Jednak w naszym przypadku i tak mamy wystarczająco dobre doprecyzowanie.
         */
    @Around("execution(* com.example.tomasz1452.model.ProjectService.createGroup(..))")
    Object aroundProjectCreateGroup(ProceedingJoinPoint jp){
        return projectCreateGroupTimer.record(() -> {
            try {
                 return jp.proceed();
            } catch (Throwable e) {
                if(e instanceof RuntimeException){
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        });
    }

    @Before("execution(* com.example.tomasz1452.model.ProjectService.createGroup(..))")
    void logMethodCall(JoinPoint jp){
        logger.info("Before {} with {}", jp.getSignature().getName(), jp.getArgs());
    }
}
