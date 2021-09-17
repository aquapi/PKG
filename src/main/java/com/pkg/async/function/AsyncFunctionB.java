package com.pkg.async.function;

import com.pkg.async.Promise;

public interface AsyncFunctionB<T, R> {
    /**
     * @param a parameter type T
     * @return a Promise with type R
     * @since 2.4
     */

    public Promise<R> apply(T a);
}
