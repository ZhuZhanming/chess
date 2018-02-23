package com.zzming.chess.message;

import java.io.Serializable;

public class RequestMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String request;

    public RequestMessage() {
    }

    public String getRequest() {
        return request;
    }
    
    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((request == null) ? 0 : request.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
//        特殊需求,同一个对象也不相等
//        if (this == obj)
//            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RequestMessage other = (RequestMessage) obj;
        if (request == null) {
            if (other.request != null)
                return false;
        } else if (!request.equals(other.request))
            return false;
        return true;
    }

    
}
