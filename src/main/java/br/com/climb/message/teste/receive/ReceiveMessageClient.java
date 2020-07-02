package br.com.climb.message.teste.receive;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.message.topic.model.ReceiveMessage;

public class ReceiveMessageClient extends GenericTcpClient<String> {

    public ReceiveMessageClient(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

    public static void main(String[] args) {

        TcpClient discoveryClient = new ReceiveMessageClient(new ClientHandler(), "127.0.0.1",3254);
        discoveryClient.sendRequest("java");
        ReceiveMessage response = (ReceiveMessage) discoveryClient.getResponse();

        response.getMessages().forEach(sendMessage -> {
            System.out.println(sendMessage.getMessage());
        });

        discoveryClient.closeConnection();
        System.out.println("Resposta:" + response);


    }
}