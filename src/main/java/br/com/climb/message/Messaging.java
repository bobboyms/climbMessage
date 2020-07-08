package br.com.climb.message;

import br.com.climb.message.exception.TypeNotSupported;

public interface Messaging {
    void execute() throws TypeNotSupported;
}
