package br.com.climb.message.server;

import br.com.climb.message.Messaging;
import br.com.climb.message.exception.TypeNotSupported;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageExecutor {

    public void execute(final Messaging messaging) {
        new Thread(new ExecuteParallel(messaging)).start();
    }

    public static class ExecuteParallel implements Runnable {

        private static final Logger logger = LoggerFactory.getLogger(ExecuteParallel.class);

        private final Messaging messaging;

        public ExecuteParallel(Messaging messaging) {
            this.messaging = messaging;
        }

        @Override
        public void run() {
            try {
                messaging.execute();
            } catch (TypeNotSupported typeNotSupported) {
                typeNotSupported.printStackTrace();
            }
        }
    }

}
