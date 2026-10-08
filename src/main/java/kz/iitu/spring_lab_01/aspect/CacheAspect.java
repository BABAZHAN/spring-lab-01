package kz.iitu.spring_lab_01.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Order(0) // Запускается самым первым, чтобы предотвратить выполнение метода при наличии кэша
public class CacheAspect {

    private static final Logger log = LoggerFactory.getLogger(CacheAspect.class);

    // Потокобезопасная карта для хранения результатов кэша: Key = "MethodName:Args", Value = Result
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    @Around("@annotation(kz.iitu.spring_lab_01.audit.SimpleCache)")
    public Object cacheResult(ProceedingJoinPoint pjp) throws Throwable {
        String cacheKey = pjp.getSignature().toShortString() + ":" + Arrays.toString(pjp.getArgs());

        if (cache.containsKey(cacheKey)) {
            log.info("[CACHE] Return cached result for key: {}", cacheKey);
            return cache.get(cacheKey);
        }

        log.info("[CACHE] Cache miss for key: {}. Executing target method...", cacheKey);
        Object result = pjp.proceed();

        if (result != null) {
            cache.put(cacheKey, result);
        }

        return result;
    }
}