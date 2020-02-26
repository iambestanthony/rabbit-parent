package com.kayakwise.rabbit.producer.service;

import com.kayakwise.rabbit.producer.constant.BrokerMessageStatus;
import com.kayakwise.rabbit.producer.entity.BrokerMessage;
import com.kayakwise.rabbit.producer.mapper.BrokerMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName MessageStoreService
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/27 0:22
 * @Version 1.0
 **/
@Service
public class MessageStoreService {

    @Autowired
    private BrokerMessageMapper brokerMessageMapper;

    public int insert(BrokerMessage brokerMessage) {
        return this.brokerMessageMapper.insert(brokerMessage);
    }

    public void success(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_OK.getCode(), new Date());
    }

    public void failure(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_FAIL.getCode(), new Date());
    }
}
