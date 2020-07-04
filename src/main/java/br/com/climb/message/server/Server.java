package br.com.climb.message.server;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.message.rpc.RpcManager;
import br.com.climb.message.topic.TopicManager;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

public class Server implements TcpServer {

    public final ConfigFile configFile;

    public Server(ConfigFile configFile) {
        this.configFile = configFile;
    }

    @Override
    public void start() throws Exception {

        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger1", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec1", new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()));
        acceptor.setHandler( new ServerHandler(new TopicManager(), new RpcManager()) );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(new Integer(configFile.getLocalPort())));

        System.out.println("-----------------------------------------------------");
        System.out.println("--                                                 --");
        System.out.println("--          STARTUP SERVER CLIMB MESSAGE           --");
        System.out.println("--                                                 --");
        System.out.println("-----------------------------------------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("##### LOCAL PORT: " + configFile.getLocalPort());
        System.out.println("");
        System.out.println("");

    }
}
