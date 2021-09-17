package com.pkg.async.function;

import com.pkg.async.Promise;

public interface AsyncFunctionA<T> {
    /**
     * @return a promise with type T
     * @since 2.4
     */

    public Promise<T> get();
}
