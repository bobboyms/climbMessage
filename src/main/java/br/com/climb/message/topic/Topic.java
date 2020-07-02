package br.com.climb.message.topic;

import br.com.climb.message.topic.model.SendMessage;

import java.util.List;

public interface Topic {
    void addTopic(String topic, SendMessage sendMessage);

    List<SendMessage> getMessageTopic(String topic);

    void removeMessageTopic(String topic, List<SendMessage> sendMessages);
}
