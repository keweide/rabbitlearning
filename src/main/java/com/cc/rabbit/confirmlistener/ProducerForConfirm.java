package com.cc.rabbit.confirmlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class ProducerForConfirm {

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
        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirmlistener.save";
        String msg = "hello confirmlistener message";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        //6.添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //deliveryTag--唯一标签
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                //成功
                System.out.println("--------------Ack------------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                //失败
                System.out.println("--------------Nack------------");
            }
        });

    }
}
