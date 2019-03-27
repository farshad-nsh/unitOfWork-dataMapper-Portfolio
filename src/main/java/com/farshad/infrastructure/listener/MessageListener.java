package com.farshad.infrastructure.listener;



import com.farshad.infrastructure.annotations.DomainEventListener;
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
public class MessageListener {


    private HashMap<String, Class<? extends AbstractMessageListener>> messageListeners;

    /**
     * example: set: BondProtos ---> nav
     * it calculates nav of the given proto
     */
    private HashMap<String, String> updateListeners;//map proto to calculatorName

    private Class messageListenerClass;
    private Set<BeanDefinition> beans;

    private String targetPackage;

    public MessageListener() {
        this.messageListeners = new HashMap<>();
        this.updateListeners=new HashMap<>();
    }

    private HashMap<String, Class<? extends AbstractMessageListener>> registerIntegrationEventListeners() throws ClassNotFoundException {
        HashMap<String, Class<? extends AbstractMessageListener>> messageListenerList = new HashMap<>();
            Class[] messageClasses=((DomainEventListener) messageListenerClass.getAnnotation(DomainEventListener.class)).ExtMessageClasses();
            String[] updateRequired=((DomainEventListener) messageListenerClass.getAnnotation(DomainEventListener.class)).calculatorNames();
        for (Class messageClass : messageClasses) {
                System.out.println("found listener:"+messageListenerClass.getCanonicalName());
                messageListenerList.put(messageClass.getCanonicalName(), messageListenerClass);
            for (String updateCalculator:updateRequired
                 ) {
                System.out.println("putting calculators.....");
                updateListeners.put(messageClass.getCanonicalName(), updateCalculator);
            }
        }
        return messageListenerList;
    }


    @PostConstruct
    public void init() throws ClassNotFoundException {
        this.targetPackage = "com.farshad";
        this.beans=findBeans();
        for (BeanDefinition bd : beans) {
            messageListenerClass = Class.forName(bd.getBeanClassName());
        }
        this.messageListeners = registerIntegrationEventListeners();
    }

    private Set<BeanDefinition> findBeans() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(DomainEventListener.class));
        Set<BeanDefinition> beans = provider.findCandidateComponents(targetPackage);
        return beans;
    }

    public Class<? extends AbstractMessageListener> getMessageListener(String eventClassName) {
        return messageListeners.getOrDefault(eventClassName, null);
    }

    public Class<? extends AbstractMessageListener> getMessageListener(Class eventClass) {
        return getMessageListener(eventClass.getCanonicalName());
    }

    public HashMap<String, String> getUpdateTasks(){
        return updateListeners;
    }


}

