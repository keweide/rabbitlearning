package com.cc.rabbit.qos;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerForQos {

    public static void main(String[] args) throws Exception{
        //1.创建一个ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        
        //2.创建一个连接
        connectionFactory.setHost("192.168.244.131");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        
        //3.通过Connection创建一个Channel
        Channel channel = connection.createChannel();

        //4.指定我们消息投递模式：消息的确认模式
        channel.confirmSelect();

        //5.通过channel发送数据
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.save";
        String msg = "hello qos message";

        for(int i = 0;i< 5;i++){
            channel.basicPublish(exchangeName, routingKey,true, null, msg.getBytes());
        }


    }
}
