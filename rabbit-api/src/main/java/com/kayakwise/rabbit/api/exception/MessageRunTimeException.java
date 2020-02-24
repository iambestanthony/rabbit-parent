package com.kayakwise.rabbit.api.exception;

/**
 * @ClassName MessageRunTimeException
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/25 0:58
 * @Version 1.0
 **/
public class MessageRunTimeException extends RuntimeException {

    private static final long serialVersionUID = -7287376688417919481L;

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message,cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
