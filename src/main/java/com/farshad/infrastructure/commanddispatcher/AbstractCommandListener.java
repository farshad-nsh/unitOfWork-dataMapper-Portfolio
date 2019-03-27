package com.farshad.infrastructure.commanddispatcher;

import com.google.protobuf.Message;

public abstract class AbstractCommandListener<M extends Message> {
    public abstract void execute(M message);
}
