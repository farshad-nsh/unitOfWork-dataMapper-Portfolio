package com.farshad.domain.listeners;
import com.farshad.infrastructure.eventdispatcher.MessageDispatcherImpl;
import com.farshad.infrastructure.listener.AbstractMessageListener;
import com.farshad.infrastructure.annotations.DomainEventListener;

import com.farshad.infrastructure.protos.generated.domain.BondProtos;

@DomainEventListener(ExtMessageClasses = {BondProtos.Bond.class} , calculatorNames = {"nav"})
public class BondListener extends AbstractMessageListener<BondProtos.Bond> {

    public void setSubject(MessageDispatcherImpl messageDispatcher){
        this.messageDispatcherImpl=messageDispatcher;
        this.messageDispatcherImpl.attach(this);
    }

    @Override
    public void handle(BondProtos.Bond message)  {
        System.out.println("handled bond successfully!");
        System.out.println("message.getName()="+message.getName());
        System.out.println("message.getValue()="+message.getValue());
    }
}
