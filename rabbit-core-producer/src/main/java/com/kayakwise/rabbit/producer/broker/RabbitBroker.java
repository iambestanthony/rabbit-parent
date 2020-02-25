package com.kayakwise.rabbit.producer.broker;

import com.kayakwise.rabbit.api.Message;

/**
 * 具体发送不同种类消息的接口
 */

public interface RabbitBroker {

    void repidSend(Message message);

    void confirmSend(Message message);

    void reliantSend(Message message);


}
