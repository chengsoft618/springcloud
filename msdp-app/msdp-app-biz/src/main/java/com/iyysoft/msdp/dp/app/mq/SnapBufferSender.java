package com.iyysoft.msdp.dp.app.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: 码农
 * @Date: 2019/8/22 18:37
 */
@Slf4j
@Component
public class SnapBufferSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private final static String EXPIRATION = 1000 * 60 * 30 + "";

    public void send(String msg) {
        log.debug("SnapShotBufferSender:{},time:{}", msg, LocalDateTime.now());
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(EXPIRATION);
        Message message = new Message(msg.getBytes(), messageProperties);
        rabbitTemplate.send(MqConstant.SNAP_BUFFER_EXCHANGE, MqConstant.SNAP_BUFFER_ROUTING_KEY, message);
    }
}
