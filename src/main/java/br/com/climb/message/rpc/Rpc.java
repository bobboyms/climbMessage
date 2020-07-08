package br.com.climb.message.rpc;

import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;
import java.util.List;

public interface Rpc {

    void addRequest(String key, RpcRequest rpcRequest);
    void addResponse(String key, RpcResponse rpcResponse);
    List<RpcRequest> getRequestList(List<String> methods);
    RpcResponse getResponse(String key);

}
