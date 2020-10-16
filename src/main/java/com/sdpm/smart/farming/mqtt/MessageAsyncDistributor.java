package com.sdpm.smart.farming.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author rukey
 */
public class MessageAsyncDistributor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MessageAsyncDistributor.class);
    private Map<String, MessageHandler> messageHandlers = new HashMap<>(16);
    private BlockingDeque<MessageWithTopic> messages = new LinkedBlockingDeque<>();
    private ExecutorService executorService;
    private Boolean running;

    public MessageAsyncDistributor() {
        // 初始化线程池
        executorService = Executors.newFixedThreadPool(10);
    }


    @Override
    public void run() {
        this.running = true;
        while (running) {
            MessageWithTopic messageWithTopic;
            try {
                messageWithTopic = messages.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            String topic = messageWithTopic.getTopic();

            String handleId = topic.substring(0, topic.lastIndexOf('/') + 1) + "+";
            MessageHandler messageHandler = messageHandlers.getOrDefault(handleId, null);
            if (Objects.nonNull(messageHandler)) {
                messageHandler.put(messageWithTopic);
            }

        }
    }

    public void subscribeMessageHandler(String topic, MessageHandler handler) {
        // 启动数据处理器
        executorService.submit(handler);
        this.messageHandlers.put(topic, handler);
        logger.info("Subscribe to topic {} and start the handler thread.", topic);
    }

    public void unsubscribeMessageHandler(String topic) {
        MessageHandler handler = this.messageHandlers.getOrDefault(topic, null);
        if (Objects.nonNull(handler)) {
            // 停止线程
            handler.stop();
            // 从线程移除
            messageHandlers.remove(topic);
            logger.info("Unsubscribe to topic {} and stop the handler thread.", topic);
        }
    }

    public void distribute(MessageWithTopic message) {
        logger.info("Message arrived: {}", message);
        this.messages.add(message);
    }

    public void stop() {
        this.running = false;
        // 停止所有数据处理器线程
        this.messageHandlers.forEach((topic, handler) -> handler.stop());

    }

}
