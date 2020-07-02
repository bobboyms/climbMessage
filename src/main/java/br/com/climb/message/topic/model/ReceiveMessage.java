package br.com.climb.message.topic.model;

import java.io.Serializable;
import java.util.List;

public class ReceiveMessage implements Serializable {

    private final String topicName;
    private final List<SendMessage> messages;

    public ReceiveMessage(String topicName, List<SendMessage> messages) {
        this.topicName = topicName;
        this.messages = messages;
    }

    public String getTopicName() {
        return topicName;
    }

    public List<SendMessage> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ReceiveMessage{" +
                "topicName='" + topicName + '\'' +
                ", messages=" + messages +
                '}';
    }
}
