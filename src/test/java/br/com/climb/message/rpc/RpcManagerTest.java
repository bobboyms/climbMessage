package br.com.climb.message.rpc;

import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.SendMessage;
import br.com.climb.message.rpc.model.KeyRpc;
import br.com.climb.message.rpc.model.RpcRequest;
import br.com.climb.message.rpc.model.RpcResponse;
import br.com.climb.message.teste.rpc.request.GetKeyHandler;
import br.com.climb.message.teste.rpc.request.SendKeyRpc;
import br.com.climb.message.teste.rpc.request.SendResponseRpc;
import br.com.climb.message.teste.rpc.send.GetHandler;
import br.com.climb.message.teste.rpc.send.GetRequestRpc;
import br.com.climb.message.teste.rpc.send.SendtHandler;
import br.com.climb.message.teste.rpc.send.SendRequestRpc;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

class RpcManagerTest {

    public static Integer somar(int a, int b) {
        return a + b;
    }

    @Test
    void addRequest() throws InterruptedException {

        new Thread(()->{
//
//            RpcManager manager = new RpcManager();
//
            List<KeyRpc> chamadas = new ArrayList<>();

            IntStream.range(0, 5000).forEach(index -> {
                String uuid = UUID.randomUUID().toString();
                chamadas.add(new KeyRpc(uuid, KeyRpc.TYPE_GET_RESPONSE_ONE));
                RpcRequest request = new RpcRequest(uuid, "somar", new Object[]{index,2});

                TcpClient discoveryClient = new SendRequestRpc(new SendtHandler(), "127.0.0.1",3254);
                discoveryClient.sendRequest(request);
                Integer response = (Integer) discoveryClient.getResponse();
                discoveryClient.closeConnection();

                System.out.println("Resposta: " + response);

            });

            while (true) {

                chamadas.stream().forEach(key -> {

                    try {
                        Thread.sleep(0,100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    TcpClient discoveryClient = new GetRequestRpc(new GetHandler(), "127.0.0.1",3254);
                    discoveryClient.sendRequest(key);
                    Object response = discoveryClient.getResponse();
                    discoveryClient.closeConnection();

                    System.out.println("Resposta: " + response);

                });

            }

        }).start();

        //processa a requisição
        new Thread(()-> {

            while (true) {

                try {
                    Thread.sleep(0,100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TcpClient discoveryClient = new SendKeyRpc(new GetKeyHandler(), "127.0.0.1",3254);
                discoveryClient.sendRequest(new KeyRpc("", KeyRpc.TYPE_GET_RESPONSE_LIST));
                Object result = discoveryClient.getResponse();
                discoveryClient.closeConnection();

                if (result.getClass() == Integer.class) {
                    continue;
                }

                List<RpcRequest> rpcRequests = (List<RpcRequest>)result;

//                System.out.println("RPC: " + rpcRequests);

                rpcRequests.forEach(rpcRequest -> {
                    Object rsult = somar((Integer) rpcRequest.getArgs()[0], (Integer) rpcRequest.getArgs()[1]);

                    TcpClient resp = new SendResponseRpc(new br.com.climb.message.teste.rpc.request.SendtHandler(), "127.0.0.1",3254);
                    resp.sendRequest(new RpcResponse(rpcRequest.getUuid(), 200 ,rsult));
                    Integer response = (Integer) resp.getResponse();
                    System.out.println("Processou: " + response);
                    resp.closeConnection();

//                    System.out.println("Resp para o servidor: " + response);

                });

//                manager.receiveResponse(rpcResponses);
            }

        }).start();

        Thread.sleep(900000);

    }

}