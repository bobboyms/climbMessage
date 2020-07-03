package br.com.climb.message.rpc.model;

import java.io.Serializable;
import java.util.Objects;

public class KeyRpc implements Serializable {

    public static final String TYPE_GET_RESPONSE_ONE = "TGRO";
    public static final String TYPE_GET_RESPONSE_LIST = "TGRL";

    private final String uuid;
    private final String type;

    public KeyRpc(String uuid, String type) {
        this.uuid = uuid;
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyRpc keyRpc = (KeyRpc) o;
        return Objects.equals(uuid, keyRpc.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "KeyRpc{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
