package com.farshad.infrastructure.commanddispatcher;


import com.farshad.infrastructure.annotations.CommandHandler;
import com.farshad.infrastructure.annotations.DomainEventListener;
import com.farshad.infrastructure.listener.AbstractMessageListener;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Set;

@Scope(value = "singleton")
@Component
public class CommandListener {

    private HashMap<String, Class<? extends AbstractCommandListener>> commandListener;

    private Class commandListenerClass;
    private Set<BeanDefinition> beans;

    private String targetPackage;


    public CommandListener() {
        this.commandListener = new HashMap<>();
    }

    private HashMap<String, Class<? extends AbstractCommandListener>> registerCommandListeners() throws ClassNotFoundException {
        HashMap<String, Class<? extends AbstractCommandListener>> commandListenerList = new HashMap<>();
        Class[] messageClasses=((CommandHandler) commandListenerClass.getAnnotation(CommandHandler.class)).ExtMessageClasses();
        for (Class messageClass : messageClasses) {
            System.out.println("found listener:"+commandListenerClass.getCanonicalName());
            commandListenerList.put(messageClass.getCanonicalName(), commandListenerClass);
        }
        return commandListener;
    }

    @PostConstruct
    public void init() throws ClassNotFoundException {
         this.targetPackage = "com.farshad";
        this.beans=findBeans();
        for (BeanDefinition bd : beans) {
            commandListenerClass = Class.forName(bd.getBeanClassName());
        }
         this.commandListener = registerCommandListeners();
    }

    private Set<BeanDefinition> findBeans() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(CommandHandler.class));
        Set<BeanDefinition> beans = provider.findCandidateComponents(targetPackage);
        return beans;
    }

    public Class<? extends AbstractCommandListener> getCommandListener(String eventClassName) {
        return commandListener.getOrDefault(eventClassName, null);
    }

    public Class<? extends AbstractCommandListener> getCommandListener(Class eventClass) {
        return getCommandListener(eventClass.getCanonicalName());
    }


}
