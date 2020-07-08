package br.com.climb.message.rpc;

import br.com.climb.commons.model.Message;
import br.com.climb.commons.model.rpc.KeyRpc;
import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.message.Messaging;
import br.com.climb.message.exception.TypeNotSupported;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RpcService implements Messaging {

    private final Message messageType;
    private final IoSession session;
    private final Object object;
    private final Rpc manager;

    public RpcService(Message messageType, IoSession session, Object object) throws TypeNotSupported {
        this.messageType = messageType;
        this.session = session;
        this.object = object;
        this.manager = getRpcManager();
    }

    private Rpc getRpcManager() throws TypeNotSupported {

        if (messageType.getTypeOfMessage().equals(Message.TYPE_RPC)) {
            return new RpcManager();
        }

        throw new TypeNotSupported("Tipo não suportado. Requerido:  RpcManager.class");
    }

    private void getResponseList(final KeyRpc key) {
        List<RpcRequest> rpcRequests = manager.getRequestList(key.getMethods());
        if (Objects.isNull(rpcRequests)) {
            rpcRequests = new ArrayList<>();
        }
        session.write(rpcRequests);
        session.closeOnFlush();
    }

    private void getResponseOne(final KeyRpc key) {
        RpcResponse response = manager.getResponse(key.getUuid());
        if (Objects.isNull(response)) {
            response = new RpcResponse("",400, Message.TYPE_RPC, null);
        }
        session.write(response);
        session.closeOnFlush();
    }

    private void getResponse() {

        final KeyRpc key = (KeyRpc) object;

        if (key.getType().equals(KeyRpc.TYPE_GET_RESPONSE_ONE)) {
            getResponseOne(key);
        }

        if (key.getType().equals(KeyRpc.TYPE_GET_RESPONSE_LIST)) {
            getResponseList(key);
        }
    }

    private void receiveMessage() {
        final RpcRequest request = (RpcRequest) object;
        manager.addRequest(request.getUuid(), request);
        session.write(200);
        session.closeOnFlush();
    }

    private void sendProcessedMessage() {
        RpcResponse response = (RpcResponse) object;
        manager.addResponse(response.getUuid(), response);
        session.write(200);
        session.closeOnFlush();
    }

    @Override
    public void execute() throws TypeNotSupported {

        if (object.getClass() == KeyRpc.class) {
            getResponse();
        }

        if (object.getClass() == RpcRequest.class) {
            receiveMessage();
        }

        if (object.getClass() == RpcResponse.class) {
            sendProcessedMessage();
        }

    }

}
