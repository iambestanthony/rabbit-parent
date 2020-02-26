package com.kayakwise.rabbit.common.convert;


import com.google.common.base.Preconditions;
import com.kayakwise.rabbit.common.serializer.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.lang.reflect.Type;

/**
 * @ClassName GenericMessageConverter
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/26 22:47
 * @Version 1.0
 **/
public class GenericMessageConverter  implements MessageConverter {

    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }


    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(this.serializer.serializeRaw(object),messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.serializer.serialize(message.getBody());
    }
}
