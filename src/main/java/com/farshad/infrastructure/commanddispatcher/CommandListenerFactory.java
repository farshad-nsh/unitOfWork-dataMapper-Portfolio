package com.farshad.infrastructure.commanddispatcher;

import com.farshad.infrastructure.listener.AbstractMessageListener;
import com.farshad.infrastructure.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class CommandListenerFactory {

    private final ApplicationContext context;

    private final CommandListener commandListener;

    private HashMap<String,String> updateListener;

    @Autowired
    public CommandListenerFactory(ApplicationContext context, CommandListener commandListener) {
        this.context = context;
        this.commandListener = commandListener;
    }

    public AbstractCommandListener getListeners(Class eventClass){
        Class<? extends AbstractCommandListener> listenerClass = commandListener.getCommandListener(eventClass);
        if(listenerClass != null) {
            return context.getBean(listenerClass);
        }
        return null;
    }


    public boolean isSubscribeToEvent(String eventClassName){
        if(commandListener.getCommandListener(eventClassName) != null){
            return true;
        }else {
            return false;
        }
    }
}
