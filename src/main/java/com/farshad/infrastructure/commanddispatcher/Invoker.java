package com.farshad.infrastructure.commanddispatcher;


import com.google.protobuf.Message;

public class Invoker<M extends Message> {

    AbstractCommandListener abstractCommandListener;

    public Invoker(AbstractCommandListener abstractCommandListener) {
        this.abstractCommandListener = abstractCommandListener;
    }

    public void setCommand(AbstractCommandListener abstractCommandListener) {
        this.abstractCommandListener = abstractCommandListener;
    }

    public void invoke(M message) {
        abstractCommandListener.execute(message);
    }

}
