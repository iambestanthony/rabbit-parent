package com.kayakwise.rabbit.api;

/**
 * @ClassName MessageType
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/25 0:29
 * @Version 1.0
 **/
public final class MessageType {

    /**
     * 迅速消息：不需要保障消息的可靠性，也不需要做confirm确认
     */
    public final static String RAPID = "0";

    /**
     * 确认消息：不需要保障消息的可靠性，但是会做消息的confirm确认
     */
    public final static String CONFIRM = "1";

    /**
     * 可靠性消息：一定要保障消息的100%可靠性投递，不允许有任何消息的丢失
     * ps：保障数据库和所发的消息的原子性的（最终一致的）
     */
    public static final String RELIANT = "2";
}
