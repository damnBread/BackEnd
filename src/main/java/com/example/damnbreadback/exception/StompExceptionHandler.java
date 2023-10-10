package com.example.damnbreadback.exception;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Component
public class StompExceptionHandler extends StompSubProtocolErrorHandler {

    private static final byte[] EMPTY_PAYLOAD = new byte[0];

    public StompExceptionHandler() {
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage,
                                                              Throwable ex) {

        return super.handleClientMessageProcessingError(clientMessage, ex);

    }


    //2
    @Override
    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor,
                                             byte[] errorPayload, Throwable cause, StompHeaderAccessor clientHeaderAccessor) {

        return MessageBuilder.createMessage(errorPayload, errorHeaderAccessor.getMessageHeaders());

//        return super.handleInternal(errorHeaderAccessor, errorPayload, cause, clientHeaderAccessor);
    }
}