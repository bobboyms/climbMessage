package br.com.climb.message;

import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import br.com.climb.message.server.ServerFactory;
import br.com.climb.message.server.TcpServer;

import java.io.IOException;

public class ClimbApplication {

    public static ConfigFile configFile;

    private static void loadConfigurations(Class<?> mainclass) throws IOException, ConfigFileException {

        configFile = new FactoryConfigFile().getConfigFile("framework.properties");
    }

    private static void startTcpServer() throws Exception {
        TcpServer server = ServerFactory.createWebServer(configFile);
        server.start();
    }

    public static void run(Class<?> mainclass) throws Exception {
        loadConfigurations(mainclass);
        startTcpServer();
    }

}
