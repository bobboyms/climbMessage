package br.com.climb.message.server;

import br.com.climb.commons.configuration.ConfigFile;

public class ServerFactory {

    public static TcpServer createWebServer(ConfigFile configFile) {
        return new Server(configFile);
    }

}
