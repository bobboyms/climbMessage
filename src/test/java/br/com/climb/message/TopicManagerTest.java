package br.com.climb.message;

import br.com.climb.message.topic.model.SendMessage;
import br.com.climb.message.topic.TopicManager;
import org.junit.jupiter.api.Test;

import java.util.List;

class TopicManagerTest {

    @Test
    void addTopic() throws InterruptedException {

        String topic = "program";

        new Thread(() -> {

            TopicManager topicManager = new TopicManager();

            for (int i = 0; i < 10; i++) {

                SendMessage sendMessage = new SendMessage(topic, i);
                System.out.println(sendMessage);

                topicManager.addTopic(topic, sendMessage);

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();

        new Thread(() -> {

            TopicManager topicManager = new TopicManager();

            while (true) {

                List<SendMessage> sendMessages = topicManager.getMessageTopic(topic);

                if (sendMessages != null) {
                    System.out.println("SendMessage: " + sendMessages);
                    topicManager.removeMessageTopic(topic, sendMessages);
                }

                try {
                    Thread.sleep(48);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();

        Thread.sleep(1000);

    }

    @Test
    void getMessageTopic() {
    }
}