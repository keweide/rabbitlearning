package com.cc.rabbit.designconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 自定义的消费者
 */
public class MyConsumer extends DefaultConsumer {

    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

        System.out.println("-------------consumer message-------------");
        System.out.println("consumerTag：" + consumerTag);
        System.out.println("envelope：" + envelope);
        System.out.println("properties：" + properties);
        System.out.println("body：" + new String(body));
    }
}
