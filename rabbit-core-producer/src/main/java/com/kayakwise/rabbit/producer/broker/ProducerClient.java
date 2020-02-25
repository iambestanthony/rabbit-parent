package com.kayakwise.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.kayakwise.rabbit.api.Message;
import com.kayakwise.rabbit.api.MessageProducer;
import com.kayakwise.rabbit.api.MessageType;
import com.kayakwise.rabbit.api.SendCallback;
import com.kayakwise.rabbit.api.exception.MessageRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ProducerClient
 * @Description 发送消息的实际实现类
 * @Author Jaycrees
 * @Date 2020/2/25 22:36
 * @Version 1.0
 **/
@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message.getTopic());
        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitBroker.repidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitBroker.reliantSend(message);
                break;
        }
    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {

    }

    @Override
    public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {

    }
}
