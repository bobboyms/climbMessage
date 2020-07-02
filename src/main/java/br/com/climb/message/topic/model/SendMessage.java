package br.com.climb.message.topic.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class SendMessage implements Serializable {

    private String uuid;
    private final String topicName;
    private final Object message;

    public SendMessage(String topicName, Object message) {
        this.topicName = topicName;
        this.message = message;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getTopicName() {
        return topicName;
    }

    public Object getMessage() {
        return message;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessage sendMessage1 = (SendMessage) o;
        return Objects.equals(uuid, sendMessage1.uuid) &&
                Objects.equals(message, sendMessage1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, message);
    }

    @Override
    public String toString() {
        return "SendMessage{" +
                "uuid='" + uuid + '\'' +
                ", message=" + message +
                '}';
    }


}
