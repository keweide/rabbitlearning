package com.cc.rabbit.quickstart;

import com.rabbitmq.client.*;

public class Consumer {

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
        
        //4.声明一个队列
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);
        
        //5.创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        
        //6.设置channel
        channel.basicConsume(queueName, true, consumer);
        
        //7.获取消息
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费："+msg);
            
        }
    }
}
