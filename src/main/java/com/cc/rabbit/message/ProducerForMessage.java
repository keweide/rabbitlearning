package com.cc.rabbit.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class ProducerForMessage {

    public static void main(String[] args) throws Exception{
        //1.创建一个ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        
        //2.创建一个连接
        connectionFactory.setHost("192.168.244.130");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        
        //3.通过Connection创建一个Channel
        Channel channel = connection.createChannel();

        Map<String,Object> header = new HashMap<>();
        header.put("111","222");
        header.put("333","444");
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .headers(header)
                .build();


        //4.通过channel发送数据
        String msg = "hello";
        channel.basicPublish("", "test001", properties, msg.getBytes());
        
        //5.关闭连接
        channel.close();
        connection.close();
    }
}
