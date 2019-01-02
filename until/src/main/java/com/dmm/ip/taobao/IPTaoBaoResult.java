package com.dmm.ip.taobao;

import java.io.Serializable;

public class IPTaoBaoResult <T>  implements Serializable {

    private String code;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
