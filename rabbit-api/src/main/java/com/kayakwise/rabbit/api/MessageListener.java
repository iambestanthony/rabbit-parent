package com.kayakwise.rabbit.api;

/**
 * 消费者监听消息
 */
public interface MessageListener {

    void onSuccess();

    void onFailure();

}
