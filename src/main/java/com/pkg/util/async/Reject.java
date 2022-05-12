package com.pkg.util.async;

public interface Reject<T extends Exception> {
    public void reject(T exc);
} 