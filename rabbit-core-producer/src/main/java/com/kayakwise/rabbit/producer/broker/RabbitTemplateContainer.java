package com.kayakwise.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.kayakwise.rabbit.api.Message;
import com.kayakwise.rabbit.api.MessageType;
import com.kayakwise.rabbit.api.exception.MessageException;
import com.kayakwise.rabbit.api.exception.MessageRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName RabbitTemplateContainer
 * @Description 池化RabbitTemplate封装
 * 1.每一个topic对应一个rabbitTemplate
 * 2.提高发送效率
 * 3.可以根据不同的需求
 * @Author Jaycrees
 * @Date 2020/2/26 0:27
 * @Version 1.0
 **/
@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback{

    private Map<String /* topic */, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private Splitter splitter = Splitter.on("#");

    @Autowired
    private ConnectionFactory connectionFactory;

    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate reRabbitTemplate = rabbitMap.get(topic);
        if (reRabbitTemplate != null) {
            return reRabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate# topic:{} is not exists,create one", topic);

        RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);
        newTemplate.setRetryTemplate(new RetryTemplate());
        newTemplate.setRoutingKey(message.getRoutingKey());

        //对于message的序列化方式
        //newTemplate.setMessageConverter(messageConverter);

        String messageType = message.getMessageType();
        if (!MessageType.RAPID.equals(messageType)) {
            newTemplate.setConfirmCallback(this);
        }
        rabbitMap.putIfAbsent(topic, newTemplate);
        return rabbitMap.get(topic);

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //具体的消息应答
        List<String> strings = splitter.splitToList(correlationData.getId());
        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));

        if (ack) {
            log.info("send message is OK, confirm messageId:{},sendTime:{}", messageId, sendTime);
        } else {
            log.error("send message is Fail, confirm messageId:{},sendTime:{}", messageId, sendTime);
        }

    }
}
