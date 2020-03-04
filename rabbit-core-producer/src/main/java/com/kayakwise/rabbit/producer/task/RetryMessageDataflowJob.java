package com.kayakwise.rabbit.producer.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.kayakwise.rabbit.producer.broker.RabbitBroker;
import com.kayakwise.rabbit.producer.constant.BrokerMessageStatus;
import com.kayakwise.rabbit.producer.entity.BrokerMessage;
import com.kayakwise.rabbit.producer.service.MessageStoreService;
import com.kayakwise.rabbit.task.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName RetryMessageDataflowJob
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/3/4 22:28
 * @Version 1.0
 **/
@Component
@ElasticJobConfig(
        name = "com.kayakwise.rabbit.producer.task.RetryMessageDataflowJob",
        cron = "0/10 * * * * ?",
        description = "可靠性投递消息补偿任务",
        overwrite = true,
        shardingTotalCount = 1
)
@Slf4j
public class RetryMessageDataflowJob implements DataflowJob<BrokerMessage> {

    @Autowired
    private MessageStoreService messageStoreService;

    @Autowired
    private RabbitBroker rabbitBroker;

    private static final int MAX_RETRY_COUNT = 3;

    /**
     * 抓取什么数据
     *
     * @param shardingContext
     * @return
     */
    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        List<BrokerMessage> list = messageStoreService.fetchTimeOutMessage4Retry(BrokerMessageStatus.SENDING);
        log.info("-------------@@@@@ 抓取数据集合，数量：{}  @@@@@@------------" + list.size());
        return list;
    }

    /**
     * 重发
     *
     * @param shardingContext
     * @param list
     */
    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> dataList) {
        dataList.forEach(brokerMessage -> {
            String messageId = brokerMessage.getMessageId();
            if (brokerMessage.getTryCount() >= MAX_RETRY_COUNT) {
                this.messageStoreService.failure(messageId);
                log.warn("------消息设置为最终失败消息ID：{}", messageId);
            } else {
                //	每次重发的时候要更新一下try count字段
                this.messageStoreService.updateTryCount(messageId);
                this.rabbitBroker.reliantSend(brokerMessage.getMessage());
            }
        });
    }
}
