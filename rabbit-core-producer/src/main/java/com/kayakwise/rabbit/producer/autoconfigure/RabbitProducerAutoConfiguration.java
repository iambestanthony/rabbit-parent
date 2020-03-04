package com.kayakwise.rabbit.producer.autoconfigure;

import com.kayakwise.rabbit.task.annotation.EnableElasticJob;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitProducerAutoConfiguration
 * @Description 自动装配
 * @Author Jaycrees
 * @Date 2020/2/25 22:31
 * @Version 1.0
 **/
@EnableElasticJob
@Configuration//必须要这个注解，才会自动装配
@ComponentScan({"com.kayakwise.rabbit.producer"})
public class RabbitProducerAutoConfiguration {

}
