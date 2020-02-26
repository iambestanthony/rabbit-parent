package com.kayakwise.rabbit.producer.broker;

import com.kayakwise.rabbit.api.Message;
import com.kayakwise.rabbit.api.MessageType;
import com.kayakwise.rabbit.producer.constant.BrokerMessageConst;
import com.kayakwise.rabbit.producer.constant.BrokerMessageStatus;
import com.kayakwise.rabbit.producer.entity.BrokerMessage;
import com.kayakwise.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName RabbitBrokerImpl
 * @Description 真正发送不同消息的实现类
 * @Author Jaycrees
 * @Date 2020/2/25 23:08
 * @Version 1.0
 **/
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    //将RabbitTemplate 池化操作，提高效率
    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    //注入rabbitTemplate 默认是单例，那么它的效率是很慢的
    //private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageStoreService messageStoreService;

    @Override
    public void reliantSend(Message message) {
        //把数据库的消息发送日志记录好
        Date now = new Date();
        BrokerMessage brokerMessage = new BrokerMessage();
        brokerMessage.setMessageId(message.getMessageId());
        brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
        //tryCount
        brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
        brokerMessage.setCreateTime(now);
        brokerMessage.setUpdateTime(now);
        brokerMessage.setMessage(message);
        messageStoreService.insert(brokerMessage);
        //执行真正发送消息逻辑
        sendKernel(message);
    }

    @Override
    public void repidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    /**
     * 发送消息的核心方法，使用异步线程池进行发送消息
     *
     * @param message
     */
    private void sendKernel(Message message) {
        AsyncBaseQueue.submit((Runnable) ()->{
            CorrelationData correlationData = new CorrelationData(String.format("%s#%s", message.getMessageId(), System.currentTimeMillis()));
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();

            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
            rabbitTemplate.convertAndSend(topic, routingKey,  message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq,messageId:{}",message.getMessageId());
        });

    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

}
