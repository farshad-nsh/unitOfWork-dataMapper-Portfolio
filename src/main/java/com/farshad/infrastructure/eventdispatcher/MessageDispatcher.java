package com.farshad.infrastructure.eventdispatcher;

import com.google.protobuf.Message;

public interface MessageDispatcher {
    void dispatchEvent(String topic, Message message);
}

