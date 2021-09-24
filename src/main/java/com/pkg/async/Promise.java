package com.pkg.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Promise<T> {
    CompletableFuture<T> c;

    /**
     * @param r supplier
     * @since 2.4
     */

    Promise(Supplier<T> r) {
        c = CompletableFuture.supplyAsync(r);
    }

    /**
     * @apiNote default constructor
     * @since 2.4
     */

    Promise() {
    }

    /**
     * @param a value resolve in Promise
     * @since 2.4
     */

    private Promise(T a) {
        c = CompletableFuture.supplyAsync(() -> a);
    }

    /**
     * @param <U> value type
     * @param a   value to be resolve
     * @return a promise with given value
     * @since 2.4
     */

    static <U> Promise<U> resolve(U a) {
        return new Promise<>(a);
    }

    /**
     * @param i void task accept one value
     * @return a void promise
     * @since 2.4
     */

    public Promise<Void> then(Consumer<T> i) {
        Promise<Void> a = new Promise<>();
        a.c = c.thenAcceptAsync(b -> {
            i.accept(b);
        });
        return a;
    }

    /**
     * @param <R> return type of promise
     * @param i   task return a value of type R
     * @return a promise with value type R
     * @since 2.4
     */

    public <R> Promise<R> then(Function<T, R> i) {
        Promise<R> a = new Promise<>();
        a.c = c.thenApplyAsync(i);
        return a;
    }

    /**
     * @param i task to handle error
     * @return this promise
     * @since 2.4
     */

    public Promise<T> handle(Consumer<Throwable> i) {
        c.handleAsync((x, y) -> {
            if (y != null)
                i.accept(y);
            return x;
        });
        return this;
    }

    /**
     * @return a void promise
     * @since 2.4
     */

    public Promise<T> handle() {
        return this.handle(x -> {
            x.printStackTrace();
        });
    }

    /**
     * @return result of this promise after executing
     * @since 2.4
     */

    T await() {
        return c.join();
    }

    /**
     * @param <T> type of given promise
     * @param p   promise to await
     * @return result of given promise after executing
     * @since 2.4
     */

    static <T> T await(Promise<T> p) {
        return p.await();
    }

    /**
     * @apiNote this method is synchronous
     * @return a string representation of this object
     * @since 2.4
     */

    public String toString() {
        try {
            return getClass().getSimpleName() + " {" + this.c.get() + "}";
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
