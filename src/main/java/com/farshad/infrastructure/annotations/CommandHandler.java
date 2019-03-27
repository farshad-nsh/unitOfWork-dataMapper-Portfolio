package com.farshad.infrastructure.annotations;


import com.google.protobuf.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Scope(value = "prototype")
@Component
public @interface CommandHandler {
    Class<? extends Message>[] ExtMessageClasses() ;
    String type() default "userProfiling";
}
