package br.com.climb.message.teste.send;

import br.com.climb.commons.generictcpclient.GenericTcpClient;
import br.com.climb.commons.generictcpclient.GenericTcpClientHandler;
import br.com.climb.commons.generictcpclient.TcpClient;
import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.message.topic.model.SendMessage;

public class SendMessageClient extends GenericTcpClient<DiscoveryRequest> {

    public SendMessageClient(GenericTcpClientHandler clientHandler, String hostname, int port) {
        super(clientHandler, hostname, port);
    }

    public static void main(String[] args) {

        TcpClient discoveryClient = new SendMessageClient(new ClientHandler(), "127.0.0.1",3254);
        discoveryClient.sendRequest(new SendMessage("java", "Olá mundo"));
        Integer response = (Integer) discoveryClient.getResponse();

        discoveryClient.closeConnection();
        System.out.println("Resposta:" + response);


    }
}