package kz.iitu.spring_lab_01.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {

    // Выбирает все классы в пакете service
    @Pointcut("within(kz.iitu.spring_lab_01.service..*)")
    public void serviceLayer() { }

    // Выбирает все публичные методы
    @Pointcut("execution(public * *(..))")
    public void publicMethod() { }

    // Комбинированный срез: публичные методы из слоя сервисов
    @Pointcut("serviceLayer() && publicMethod()")
    public void serviceOperation() { }
}