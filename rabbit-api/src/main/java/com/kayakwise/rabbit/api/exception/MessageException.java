package com.kayakwise.rabbit.api.exception;

/**
 * @ClassName MessageException
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/25 0:55
 * @Version 1.0
 **/
public class MessageException extends Exception {

    private static final long serialVersionUID = -8171779839580620830L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message,cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
