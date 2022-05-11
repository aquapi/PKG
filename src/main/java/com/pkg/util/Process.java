package com.pkg.util;

public class Process extends Thread {
    /**
     * Start the thread and wait at most {@code timeout} for it to finish.
     * 
     * @param timeout
     */
    public synchronized void await(int timeout) throws InterruptedException {
        start();
        join(timeout);
    }

    /**
     * Interrupt the thread. If the thread successfully interrupted, returns true
     * @return true if the thread was interrupted successfully
     */
    public synchronized boolean end() {
        // Prevent this from throwing an exception
        try {
            interrupt();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Start the thread and wait for it to finish.
     * 
     * @throws InterruptedException 
     */
    public synchronized void await() throws InterruptedException {
        await(0);
    }
}
