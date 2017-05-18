package com.owen.mq;

public class MqTest {

//    
//    public static Channel getChannel(String host, String username, String password, String queueName) throws IOException, TimeoutException {
//        Connection connection = getConnection(host, username, password);
//        Channel channel = connection.createChannel();
//
//        // 第二个参数设置消息队列持久化
//        boolean durable = true;
//        channel.queueDeclare(queueName, durable, false, false, null);
//
//        // 不要一次性给消费者超过1个任务
//        int prefetchCount = 1;
//        channel.basicQos(prefetchCount);
//        return channel;
//    }
}
