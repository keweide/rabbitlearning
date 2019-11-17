package com.cc.rabbit.confirmlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ConsumerForReturn {

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
        
        //4.声明一个队列
        String exchangeName = "test_return_exchange";
        String routingKey = "return.#";
        String routingKeyError = "abc.save";
        String queueName = "test_return_queue";
        channel.exchangeDeclare(exchangeName,"topic",true);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName,exchangeName,routingKey);
        
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
