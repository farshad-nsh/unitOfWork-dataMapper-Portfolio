package com.farshad.infrastructure.annotations;

import com.farshad.domain.EventType;
import com.google.protobuf.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Scope(value = "prototype")
@Component
public @interface DomainEventListener {
    Class<? extends Message>[] ExtMessageClasses() ;
    EventType EVENT_TYPE() default EventType.NEWASSETARRIVED;
    String version() default "";
    String[] calculatorNames() default {"nav","anotherVariable"};
}
