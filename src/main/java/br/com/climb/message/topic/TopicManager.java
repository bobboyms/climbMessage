package br.com.climb.message.topic;

import br.com.climb.commons.model.SendMessage;

import java.util.*;

public class TopicManager implements Topic {

    //topico e message
    private static final Map<String, List<SendMessage>> topics = Collections.synchronizedMap(new HashMap<>()); //new ConcurrentHashMap<>();

    @Override
    public void addTopic(String topic, SendMessage sendMessage) {

        List<SendMessage> sendMessages = topics.get(topic);

        if (sendMessages == null) {
            sendMessages = Collections.synchronizedList(new ArrayList<>());
        }

        sendMessages.add(sendMessage);
        topics.put(topic, sendMessages);
    }

    @Override
    public List<SendMessage> getMessageTopic(String topic) {
        return topics.get(topic);
    }

    @Override
    public void removeMessageTopic(String topic, List<SendMessage> sendMessages) {

        final List<SendMessage> msg = topics.get(topic);
        msg.removeAll(sendMessages);

//        topics.entrySet().stream()
//                .filter(stringObjectEntry -> stringObjectEntry.getKey().equals(topic))
//                .findAny().ifPresent(entry -> {
//                    entry.getValue().removeAll(sendMessages);
//                });

//        long startTime = System.nanoTime();
//        long stopTime = System.nanoTime();
//        System.out.println(stopTime - startTime);
    }

}
