package com.zz.fundapp.http.result;


public class ObjectResult<T> extends Result {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ObjectResult{" +
                "Resultcode = " + getResultCode() +
                "Resultcode = " + getResultMsg() +
                "data=" + data +
                '}';
    }
}
