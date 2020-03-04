package com.kayakwise.rabbit.producer.service;

import com.kayakwise.rabbit.producer.constant.BrokerMessageStatus;
import com.kayakwise.rabbit.producer.entity.BrokerMessage;
import com.kayakwise.rabbit.producer.mapper.BrokerMessageMapper;
import com.sun.corba.se.pept.broker.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public BrokerMessage selectByMessageId(String messageId) {
         return this.brokerMessageMapper.selectByPrimaryKey(messageId);
    }

    public int updateTryCount(String brokerMessageId) {
        return this.brokerMessageMapper.update4TryCount(brokerMessageId, new Date());
    }

    public void success(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_OK.getCode(), new Date());
    }

    public void failure(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SEND_FAIL.getCode(), new Date());
    }

    public List<BrokerMessage> fetchTimeOutMessage4Retry(BrokerMessageStatus brokerMessageStatus) {
        return this.brokerMessageMapper.queryBrokerMessageStatus4Timeout(brokerMessageStatus.toString());
    }
}
