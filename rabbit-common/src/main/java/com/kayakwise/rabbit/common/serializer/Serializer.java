package com.kayakwise.rabbit.common.serializer;

/**
 * @ClassName Serializer
 * @Description 序列化和反序列化的接口
 * @Author Jaycrees
 * @Date 2020/2/26 22:19
 * @Version 1.0
 **/
public interface Serializer {

    byte[] serializeRaw(Object data);

    String serialize(Object data);

    <T> T deserialize(String content);

    <T> T deserialize(byte[] content);
}
