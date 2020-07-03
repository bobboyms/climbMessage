package br.com.climb.message.teste.rpc.send;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.SendMessage;
import br.com.climb.message.rpc.model.KeyRpc;
import br.com.climb.message.rpc.model.RpcRequest;
import br.com.climb.message.rpc.model.RpcResponse;

public class GetRequestRpc extends GenericTcpClient<KeyRpc> {

    public GetRequestRpc(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

    public static void main(String[] args) {

        TcpClient discoveryClient = new GetRequestRpc(new SendtHandler(), "127.0.0.1",3254);
        discoveryClient.sendRequest(new SendMessage("cliente", "Olá mundo"));
        RpcResponse response = (RpcResponse) discoveryClient.getResponse();

        discoveryClient.closeConnection();
        System.out.println("Resposta:" + response);


    }
}