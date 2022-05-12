package com.pkg.util.async;

/**
 * A Promise implementation.
 */
public final class Promise<T> extends Thread {
    private Resolver<T, Exception> resolver;
    private T value;
    private PromiseState state;
    private Exception exc;

    /**
     * Create a Promise with a resolver
     * 
     * @param resolver the resolver
     */
    public Promise(Resolver<T, Exception> resolver) {
        this.resolver = resolver;
        this.state = PromiseState.PENDING;
        start();
    }

    /**
     * Get the current state
     * 
     * @return the Promise state
     */
    public final synchronized PromiseState state() {
        return state;
    }

    /**
     * Create a promise with an initial value
     * 
     * @param value the initial value
     */
    public Promise(T value) {
        this((res, rej) -> {
            try {
                res.resolve(value);
            } catch (Exception e) {
                rej.reject(e);
            }
        });
    }

    /**
     * Start the promise. This method is called by the constructor
     */
    public void run() {
        // Change the finish to true
        resolver.run(
                value -> {
                    this.value = value;
                    state = PromiseState.RESOLVED;
                },
                exc -> {
                    this.exc = exc;
                    state = PromiseState.REJECTED;
                });

        // While the resolver is not finish
        while (state == PromiseState.PENDING)
            Thread.yield();

        resolver = null;
    }

    /**
     * Wait for the promise to complete
     * 
     * @param <R> The type of the Promise to wait
     * @param p   the Promise to wait
     * @return the result of the Promise
     * @throws Exception when exception occurs
     */
    public static synchronized <R> R await(Promise<R> p) throws Exception {
        p.join();
        if (p.exc != null)
            throw p.exc;
        return p.value;
    }

    /**
     * Wait for this Promise to return the result
     * 
     * @return the result
     * @throws Exception when exception occurs
     */
    public synchronized T await() throws Exception {
        return Promise.await(this);
    }

    /**
     * @param <R> The type of the newly created Promise
     * @param hnd The handler
     * @return a new Promise
     */
    public synchronized <R> Promise<R> then(Handler<T, R> hnd) {
        return new Promise<>((res, rej) -> {
            try {
                res.resolve(hnd.handler(await()));
            } catch (Exception e) {
                rej.reject(e);
            }
        });
    }
}
