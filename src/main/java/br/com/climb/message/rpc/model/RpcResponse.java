package br.com.climb.message.rpc.model;

import java.io.Serializable;
import java.util.Objects;

public class RpcResponse implements Serializable {

    private final String uuid;
    private final Integer statusCode;
    private final Object response;

    public RpcResponse(String uuid, Integer statusCode, Object response) {
        this.statusCode = statusCode;
        this.response = response;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Object getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcResponse response = (RpcResponse) o;
        return Objects.equals(uuid, response.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "uuid='" + uuid + '\'' +
                ", statusCode=" + statusCode +
                ", response=" + response +
                '}';
    }
}
