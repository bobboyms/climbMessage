package br.com.climb.message.server;

import br.com.climb.commons.model.KeyMessage;
import br.com.climb.commons.model.Message;
import br.com.climb.commons.model.ReceiveMessage;
import br.com.climb.commons.model.SendMessage;
import br.com.climb.commons.model.rpc.KeyRpc;
import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.message.MessagingFactory;
import br.com.climb.message.exception.TypeNotSupported;
import br.com.climb.message.rpc.RpcManager;
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

    private final Topic topic;
    private final RpcManager manager;

    public ServerHandler(Topic topicMessage, RpcManager manager) {
        this.topic = topicMessage;
        this.manager = manager;
    }

    @Override
    public void messageReceived(IoSession session, Object object) throws Exception {

        try {

            //****************** rpc ***************

            Message message = (Message)object;
            new MessageExecutor().execute(new MessagingFactory().create(message, session, object));

//            System.out.println("Receive: " + message);

//            if (message.getClass() == KeyRpc.class) {
//
//                final KeyRpc key = (KeyRpc)message;
//
//                if (key.getType().equals(KeyRpc.TYPE_GET_RESPONSE_ONE)) {
//                    RpcResponse response = manager.getResponse(key.getUuid());
//                    if (Objects.isNull(response)) {
//                        response = new RpcResponse("",400, Message.TYPE_RPC, null);
//                    }
//                    session.write(response);
//                    session.closeOnFlush();
//                    return;
//                }
//
//                if (key.getType().equals(KeyRpc.TYPE_GET_RESPONSE_LIST)) {
////                    System.out.println("key: " + key);
//                    List<RpcRequest> rpcRequests = manager.getRequestList(key.getMethods());
//                    if (Objects.isNull(rpcRequests)) {
//                        rpcRequests = new ArrayList<>();
//                    }
//                    session.write(rpcRequests);
//                    session.closeOnFlush();
//                    return;
//                }
//            }
//
//            if (message.getClass() == RpcRequest.class) {
//                RpcRequest request = (RpcRequest)message;
//                manager.addRequest(request.getUuid(), request);
//                session.write(200);
//                session.closeOnFlush();
//                return;
//            }
//
//            if (message.getClass() == RpcResponse.class) {
//                RpcResponse response = (RpcResponse) message;
//                manager.addResponse(response.getUuid(), response);
//                session.write(200);
//                session.closeOnFlush();
//                return;
//            }
//
//            //******************* messageria ******************//
//
//            if (message.getClass() == KeyMessage.class) {
//
//                List<SendMessage> sendMessages = topic.getMessageTopic(((KeyMessage) message).getTopic());
//
//                if (!Objects.isNull(sendMessages)) {
//                    session.write(new ReceiveMessage((String) message, Message.TYPE_MESSAGE, sendMessages));
//                    topic.removeMessageTopic((String) message, sendMessages);
//                } else {
//                    session.write(new ReceiveMessage((String) message, Message.TYPE_MESSAGE, new ArrayList<>()));
//                }
//
//                session.closeOnFlush();
//
//                return;
//            }
//
//            if (message.getClass() == SendMessage.class) {
//                SendMessage topic = (SendMessage) message;
//                this.topic.addTopic(topic.getTopicName(),topic);
//                session.write(new Integer(200));
//                session.closeOnFlush();
//                return;
//            }
//
//            throw new TypeNotSupported("Tipo não suportado pelo servidor");

        } catch (Exception e) {
            logger.error("responseForClient {}", e);
            session.write(new Integer(500));
            session.closeOnFlush();
//            response.setStatus(500);
//            response.setBody(e.getMessage().getBytes());
//            session.write(response);
        }

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

        System.out.println("---- deu erro ----");
//        session.write(new Integer(500));
        session.closeNow();
        System.out.println(cause);
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
