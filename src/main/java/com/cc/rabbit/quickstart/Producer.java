package com.cc.rabbit.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public static void main(String[] args) throws Exception{
        //1.创建一个ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        
        //2.创建一个连接
        connectionFactory.setHost("192.168.145.150");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        
        //3.通过Connection创建一个Channel
        Channel channel = connection.createChannel();
        
        //4.通过channel发送数据
        String msg = "hello";
        channel.basicPublish("", "test001", null, msg.getBytes());
        
        //5.关闭连接
        channel.close();
        connection.close();
    }
}
