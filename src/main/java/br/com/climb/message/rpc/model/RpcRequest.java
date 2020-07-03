package br.com.climb.message.rpc.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class RpcRequest implements Serializable {

    private final String uuid;
    private final String methodName;
    private final Object[] args;

    public RpcRequest(String uuid, String methodName, Object[] args) {
        this.uuid = uuid;
        this.methodName = methodName;
        this.args = args;
    }

    public String getUuid() {
        return uuid;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcRequest request = (RpcRequest) o;
        return Objects.equals(uuid, request.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "uuid='" + uuid + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
