package com.pkg.util.async;

public interface Resolver<T, E extends Exception> {
    public void run(Resolve<T> res, Reject<E> rej);
}
