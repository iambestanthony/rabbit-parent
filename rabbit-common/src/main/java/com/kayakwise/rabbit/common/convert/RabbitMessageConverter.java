package com.kayakwise.rabbit.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @ClassName RabbitMessageConverter
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/26 23:03
 * @Version 1.0
 **/
public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter delegate;
    private final String defaulExprie = String.valueOf(24*60*60*1000);

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        messageProperties.setExpiration(defaulExprie);
        return this.delegate.toMessage(object, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        com.kayakwise.rabbit.api.Message msg = (com.kayakwise.rabbit.api.Message) this.delegate.fromMessage(message);
        return msg;
    }

}
