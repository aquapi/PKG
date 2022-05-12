package com.pkg.util.async;

public interface Handler<T, R> {
    public R handler(T value);
    public R errorHandler(Exception e) throws Exception;
}
