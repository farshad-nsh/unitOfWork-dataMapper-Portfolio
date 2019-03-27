package com.farshad.infrastructure.listener;


import com.farshad.infrastructure.eventdispatcher.MessageDispatcherImpl;
import com.google.protobuf.Message;

public abstract class AbstractMessageListener<M extends Message> {
    public abstract void handle(M message);
    public abstract void setSubject(MessageDispatcherImpl messageDispatcher);
    public MessageDispatcherImpl messageDispatcherImpl;
}
