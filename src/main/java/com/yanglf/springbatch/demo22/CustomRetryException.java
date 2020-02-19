package com.yanglf.springbatch.demo22;

/**
 * @author yanglf
 * @sine 2020.02.18
 * @descriptipon
 * @see
 */
public class CustomRetryException extends RuntimeException {

    public CustomRetryException() {
        super();
    }

    public CustomRetryException(String cause) {
        super(cause);

    }
}
