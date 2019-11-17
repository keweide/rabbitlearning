package com.cc.rabbit.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ProducerForReturn {

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
        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";
        String msg = "hello return message";


        //6.添加一个return监听
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("------------handle return-------------");
                System.out.println("replyCode："+ replyCode);
                System.out.println("replyText："+ replyText);
                System.out.println("exchange："+ exchange);
                System.out.println("routingKey："+ routingKey);
                System.out.println("properties："+ properties);
                System.out.println("body："+ new String(body));
            }
        });

//        channel.basicPublish(exchangeName, routingKey,true, null, msg.getBytes());

        channel.basicPublish(exchangeName, routingKeyError,true, null, msg.getBytes());
    }
}
