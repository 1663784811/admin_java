package com.cyyaw.util.tools;

public class WhyException extends RuntimeException {

    String msg;
    Integer code;

    public WhyException() {
    }

    public WhyException(String message) {
        this.msg = message;
    }

    public WhyException(String message, Integer code) {
        this.msg = message;
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }


    @Override
    public String getMessage() {
        String m = super.getMessage();
        if (null != msg) {
            m = msg;
        }
        return msg;
    }
}
