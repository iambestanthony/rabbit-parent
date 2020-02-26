package com.kayakwise.rabbit.common.serializer.impl;

import com.kayakwise.rabbit.api.Message;
import com.kayakwise.rabbit.common.serializer.Serializer;
import com.kayakwise.rabbit.common.serializer.SerializerFactory;

/**
 * @ClassName JacksonSerializerFactory
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/26 22:45
 * @Version 1.0
 **/
public class JacksonSerializerFactory implements SerializerFactory {

    public static final JacksonSerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }

}
