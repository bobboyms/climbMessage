package br.com.climb.message;

import br.com.climb.commons.model.Message;
import br.com.climb.message.exception.TypeNotSupported;
import br.com.climb.message.rpc.RpcService;
import br.com.climb.message.topic.MessageService;
import org.apache.mina.core.session.IoSession;

public class MessagingFactory {

    public Messaging create(Message message, IoSession session, Object object) throws TypeNotSupported {

        if (message.getTypeOfMessage().equals(Message.TYPE_RPC)) {
            return new RpcService(message, session, object);
        }

        if (message.getTypeOfMessage().equals(Message.TYPE_MESSAGE)) {
            return new MessageService(message, session, object);
        }

        throw new TypeNotSupported("Tipo não suportado." + message.getTypeOfMessage());

    }

}
