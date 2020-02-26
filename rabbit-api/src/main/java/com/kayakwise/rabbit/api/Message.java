package com.kayakwise.rabbit.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Message
 * @Description 消息
 * @Author Jaycrees
 * @Date 2020/2/25 0:18
 * @Version 1.0
 **/
public class Message implements Serializable {

    private static final long serialVersionUID = 7305550623761832233L;

    /* 消息的唯一ID */
    private String messageId;

    /* 消息的主题 */
    private String topic;

    /* 消息的路由规则 */
    private String routingKey = "";

    /* 消息的附加属性 */
    private Map<String, Object> attributes = new HashMap<String, Object>();

    /* 延迟消息的参数配置 */
    private int delayMills;

    /* 消息的类型 */
    private String messageType = MessageType.CONFIRM;

    public Message() {
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills, String messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
        this.messageType = messageType;
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public int getDelayMills() {
        return delayMills;
    }

    public void setDelayMills(int delayMills) {
        this.delayMills = delayMills;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
