package com.farshad.infrastructure.commanddispatcher;


import com.farshad.domain.Share;
import com.farshad.domain.commands.BuyShareCommand;
import com.farshad.domain.commands.SellShareCommand;
import com.farshad.infrastructure.listener.AbstractMessageListener;
import com.farshad.infrastructure.listener.MessageListenerFactory;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandDispatcher {

    private final CommandListenerFactory listenerFactory;

    private final ApplicationContext context;

    private AbstractCommandListener abstractCommandListener;


    private Message message;

    public CommandDispatcher(CommandListenerFactory listenerFactory, ApplicationContext context) {
        this.listenerFactory = listenerFactory;
        this.context = context;
    }

    public void call(Message message){
        abstractCommandListener = listenerFactory.getListeners(message.getClass());

        Descriptors.FieldDescriptor field1 = message.getDescriptorForType().findFieldByNumber(1);
        Descriptors.FieldDescriptor field2 = message.getDescriptorForType().findFieldByNumber(2);
        Descriptors.FieldDescriptor field3 = message.getDescriptorForType().findFieldByNumber(3);

        String name = (String) message.getField(field1);
        String type= (String) message.getField(field2);
        double value = (double) message.getField(field3);

        Share share = context.getBean(Share.class); // receiver
        AbstractCommandListener buyShareCommand = new BuyShareCommand(share); // concrete command
        AbstractCommandListener sellShareCommand= new SellShareCommand(share); //concrete command
        Invoker invoker=new Invoker(buyShareCommand);// invoker
        if (name.equals("farshadBond")){
            invoker = new Invoker(buyShareCommand);
            invoker.setCommand(buyShareCommand);
        }else{
            invoker.setCommand(sellShareCommand);
        }
        invoker.invoke(message);
    }


    public void dispatchCommand(Message message) {
        call(message);
    }

}
