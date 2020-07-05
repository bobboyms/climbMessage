package br.com.climb.message.rpc;

import br.com.climb.commons.model.rpc.RpcRequest;
import br.com.climb.commons.model.rpc.RpcResponse;

import java.util.*;
import java.util.stream.Collectors;

public class RpcManager {

    //UUID / RpcRequest
    private static final Map<String, RpcRequest> RPC_REQUEST_MAP = Collections.synchronizedMap(new HashMap<>());

    //UUID / RpcResponse
    private static final Map<String, RpcResponse> RPC_RESPONSE_MAP = Collections.synchronizedMap(new HashMap<>());

    public void addRequest(String key, RpcRequest rpcRequest) {
        RPC_REQUEST_MAP.put(key, rpcRequest);
    }

    public void addResponse(String key, RpcResponse rpcResponse) {
        RPC_REQUEST_MAP.remove(key);
        RPC_RESPONSE_MAP.put(key, rpcResponse);
    }

    public List<RpcRequest> getRequestList(List<String> methods) {

        return RPC_REQUEST_MAP.entrySet().stream()
                .filter(entry -> methods.contains(entry.getValue().getMethodName()))
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    public RpcResponse getResponse(String key) {
        RpcResponse response = RPC_RESPONSE_MAP.get(key);
        RPC_RESPONSE_MAP.remove(key);
        return response;
    }

}
