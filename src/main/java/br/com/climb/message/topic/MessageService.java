package br.com.climb.message.topic;

import br.com.climb.commons.model.KeyMessage;
import br.com.climb.commons.model.Message;
import br.com.climb.commons.model.ReceiveMessage;
import br.com.climb.commons.model.SendMessage;
import br.com.climb.message.Messaging;
import br.com.climb.message.exception.TypeNotSupported;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageService implements Messaging {

    private final Message messageType;
    private final IoSession session;
    private final Object object;
    private final Topic manager;

    public MessageService(Message messageType, IoSession session, Object object) throws TypeNotSupported {
        this.messageType = messageType;
        this.session = session;
        this.object = object;
        this.manager = getTopicManager();
    }

    private Topic getTopicManager() throws TypeNotSupported {

        if (messageType.getTypeOfMessage().equals(Message.TYPE_MESSAGE)) {
            return new TopicManager();
        }

        throw new TypeNotSupported("Tipo não suportado. Requerido:  TopicManager.class");
    }

    private void receivedMessage() throws TypeNotSupported {
        final SendMessage topic = (SendMessage) object;
        manager.addTopic(topic.getTopicName(),topic);
        session.write(new Integer(200));
        session.closeOnFlush();
    }

    private void sendListMessage() throws TypeNotSupported {

        List<SendMessage> sendMessages = manager.getMessageTopic(((KeyMessage) object).getTopic());
        final KeyMessage keyMessage = (KeyMessage) object;

        if (!Objects.isNull(sendMessages)) {
            session.write(new ReceiveMessage(keyMessage.getTopic(), Message.TYPE_MESSAGE, sendMessages));
            manager.removeMessageTopic(keyMessage.getTopic(), sendMessages);
        } else {
            session.write(new ReceiveMessage(keyMessage.getTopic(), Message.TYPE_MESSAGE, new ArrayList<>()));
        }

        session.closeOnFlush();
    }

    @Override
    public void execute() throws TypeNotSupported {

        if (object.getClass() == KeyMessage.class) {
            sendListMessage();
        }

        if (object.getClass() == SendMessage.class) {
            receivedMessage();
        }
    }

}
