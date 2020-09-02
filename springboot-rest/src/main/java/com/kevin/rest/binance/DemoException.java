package com.kevin.rest.binance;

/**
 * @author kevin chen
 */
public class DemoException extends RuntimeException {
    public DemoException(String message) {
        super(message);
    }

    public static void main(String[] args) {
        if (true) {
            throw new DemoException("DemoException");
        }
    }
}
