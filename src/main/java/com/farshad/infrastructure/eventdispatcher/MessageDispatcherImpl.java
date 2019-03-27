package com.farshad.infrastructure.eventdispatcher;


import com.farshad.infrastructure.NAVContext.NAV;
import com.farshad.infrastructure.NAVContext.Statement;
import com.farshad.infrastructure.listener.AbstractMessageListener;
import com.farshad.infrastructure.listener.MessageListenerFactory;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageDispatcherImpl implements MessageDispatcher{

    private final MessageListenerFactory listenerFactory;

    private final ApplicationContext context;

    private  AbstractMessageListener abstractMessageListener;

    private List<AbstractMessageListener> observers = new ArrayList<AbstractMessageListener>();

    private Message message;

    private NAV nav;

    @Autowired
    public MessageDispatcherImpl(MessageListenerFactory listenerFactory, ApplicationContext context) {
        this.listenerFactory = listenerFactory;
        this.context=context;
        nav=new NAV();
        nav.setInitialNAV();

    }


    public void setMessage(Message message){
        this.message=message;
        notifyAllObservers();
    }


    public void attach(AbstractMessageListener abstractMessageListener){
        observers.add(abstractMessageListener);
    }

    public void notifyAllObservers(){
        for (AbstractMessageListener observer : observers) {
            observer.handle(message);
        }
    }


    private boolean callMessageListener(Message message) throws NoSuchFieldException {
        handleUpdateTasks(message);
        abstractMessageListener = listenerFactory.getListeners(message.getClass());
        abstractMessageListener.setSubject(this);
        System.out.println("----------state change------------");
        this.setMessage(message);
        observers= new ArrayList<AbstractMessageListener>();
        return true;
    }

    private void handleUpdateTasks(Message message) throws NoSuchFieldException {

        if (listenerFactory.getUpdatingTasks().get(message.getClass().getCanonicalName()).equals("nav")) {

            Descriptors.FieldDescriptor field1 = message.getDescriptorForType().findFieldByNumber(1);
            Descriptors.FieldDescriptor field2 = message.getDescriptorForType().findFieldByNumber(2);
            Descriptors.FieldDescriptor field3 = message.getDescriptorForType().findFieldByNumber(3);

            String name = (String) message.getField(field1);
            String type= (String) message.getField(field2);
            double value = (double) message.getField(field3);

            System.out.println("name="+name+" "+"type="+type+"  value="+value);
            Statement statement = new Statement();
            statement.getName(name);
            statement.setType(type);
            statement.setValue(value);
            nav.update(statement);
            System.out.println("nav at time:"+new Timestamp(System.currentTimeMillis())
            +"="+NAV.getNAV());

        }

    }


    @Override
    public void dispatchEvent(String topic, Message message) {
        try {

            callMessageListener(message);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
