package br.com.climb.message.server;

import br.com.climb.message.topic.model.ReceiveMessage;
import br.com.climb.message.topic.model.SendMessage;
import br.com.climb.message.topic.Topic;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerHandler extends IoHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    private final Topic topicMessage;

    public ServerHandler(Topic topicMessage) {
        this.topicMessage = topicMessage;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        try {

            System.out.println("SendMessage receive: " + message);

            if (message.getClass() == String.class) {

                List<SendMessage> messages = topicMessage.getMessageTopic((String) message);

                if (!Objects.isNull(messages)) {
                    session.write(new ReceiveMessage((String) message, messages));
                    topicMessage.removeMessageTopic((String) message, messages);
                } else {
                    session.write(new ReceiveMessage((String) message, new ArrayList<>()));
                }

                return;
            }

            SendMessage topic = (SendMessage) message;
            topicMessage.addTopic(topic.getTopicName(),topic);
            session.write(new Integer(200));

        } catch (Exception e) {
            logger.error("responseForClient {}", e);
            session.write(new Integer(500));
//            response.setStatus(500);
//            response.setBody(e.getMessage().getBytes());
//            session.write(response);
        }

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

        System.out.println("---- deu erro ----");
        session.write(new Integer(500));
//        ObjectResponse response = new ObjectResponse();
//
//        if (cause.getClass() == NotFoundException.class) {
//            response.setStatus(400);
//            response.setBody(cause.toString().getBytes());
//            session.write(response);
//            return;
//        }
//
//        logger.error("Erro serverHandler {}", cause);
//
//        response.setStatus(500);
//        response.setBody(cause.toString().getBytes());
//        session.write(response);

    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
}
