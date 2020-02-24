package com.kayakwise.rabbit.api;

import com.kayakwise.rabbit.api.exception.MessageRunTimeException;

import java.util.List;

/**
 * @ClassName MessageProducer
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/25 1:01
 * @Version 1.0
 **/
public interface MessageProducer {

    /**
     * 消息的发送 附带sendcallback回调执行响应的业务
     * @param message
     * @param sendCallback
     * @throws MessageRunTimeException
     */
    void send(Message message, SendCallback sendCallback) throws MessageRunTimeException;

    void send(Message message) throws MessageRunTimeException;

    void send(List<Message> messages) throws MessageRunTimeException;
}
