package com.iyysoft.msdp.dp.app.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 码农
 * @Date: 2019/10/12 18:44
 */
@ComponentScan
@Configuration
public class AutoDeclare {

    //定义队列
    @Bean
    public Queue snapBufferQueue() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", MqConstant.SNAP_DLX_EXCHANGE);
        map.put("x-dead-letter-routing-key", MqConstant.SNAP_DLX_ROUTING_KEY);
        return new Queue(MqConstant.SNAP_BUFFER_QUEUE, true, false, false, map);
    }

    //定义队列
    @Bean
    public Queue snapDlxQueue() {
        return new Queue(MqConstant.SNAP_DLX_QUEUE, true, false, false);
    }

    //定义交换机
    @Bean
    public Exchange snapBufferExchange() {
        Exchange exchange = new DirectExchange(MqConstant.SNAP_BUFFER_EXCHANGE, true, false);
        return exchange;
    }

    //定义交换机
    @Bean
    public Exchange snapDlxExchange() {
        Exchange exchange = new DirectExchange(MqConstant.SNAP_DLX_EXCHANGE, true, false);
        return exchange;
    }

    //绑定
    @Bean
    public Binding bufferBind() {
        Binding binding = new Binding(MqConstant.SNAP_BUFFER_QUEUE, Binding.DestinationType.QUEUE, MqConstant.SNAP_BUFFER_EXCHANGE, MqConstant.SNAP_BUFFER_ROUTING_KEY, null);
        return binding;
    }

    //绑定
    @Bean
    public Binding dlxBind() {
        Binding binding = new Binding(MqConstant.SNAP_DLX_QUEUE, Binding.DestinationType.QUEUE, MqConstant.SNAP_DLX_EXCHANGE, MqConstant.SNAP_DLX_ROUTING_KEY, null);
        return binding;
    }


}
