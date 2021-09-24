package com.pkg.async;

import java.util.function.Supplier;

import com.pkg.async.function.AsyncFunctionA;
import com.pkg.async.function.AsyncFunctionB;

public class AsyncScope {
    /**
     * @apiNote Implementing this constructor is allowed for the class extends it 
     * @since 2.4
     */

    protected AsyncScope() {
    }

    /**
     * Scope instance for classes extend this class
     */

    protected static final AsyncScope scope = new AsyncScope(); 

    /**
     * @param <T> return type of async function
     * @param a   async function to be await
     * @return result of async function
     * @since 2.4
     */

    public <T> T await(AsyncFunctionA<T> a) {
        return a.get().await();
    }

    /**
     * @param <T>   parameter type
     * @param <R>   return type
     * @param a     async function
     * @param param parameter of async function
     * @return result of async function
     * @since 2.4
     */

    public <T, R> R await(AsyncFunctionB<T, R> a, T param) {
        return a.apply(param).await();
    }

    /**
     * @param <T>
     * @param a   promise to await
     * @return result after await
     * @since 2.4
     */

    public <T> T await(Promise<T> a) {
        return a.await();
    }

    /**
     * @param <T> parameter type
     * @param a
     * @return Promise of value a
     * @since 2.4
     */

    public <T> Promise<T> resolve(T a) {
        return Promise.resolve(a);
    }

    /**
     * @param <T>
     * @param a
     * @return promise of result of a
     * @since 2.4
     */

    public <T> Promise<T> resolve(Supplier<T> a) {
        return new Promise<>(a);
    }
}
