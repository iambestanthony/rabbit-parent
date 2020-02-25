package com.kayakwise.rabbit.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitProducerAutoConfiguration
 * @Description 自动装配
 * @Author Jaycrees
 * @Date 2020/2/25 22:31
 * @Version 1.0
 **/
@Configuration//必须要这个注解，才会自动装配
@ComponentScan({"com.kayakwise.rabbit.producer"})
public class RabbitProducerAutoConfiguration {

}
