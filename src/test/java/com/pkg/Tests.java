package com.pkg;

import com.pkg.async.AsyncScope;
import com.pkg.math.Complex;
import com.pkg.time.Timer;

class TimerTest {
    public static void main(String[] args) {
        new Timer().addTask(2000, System.out::println).addTask(2000, System.out::println)
                .addTask(3000, System.out::println).start();
    }
}

class AsyncTest extends AsyncScope {
    public void run() {
        async(9).then(a -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getId());
            return a;
        }).then(a -> {
            System.out.println(a);
        }).handle();
    }
    public static void main(String[] args) {
        new AsyncTest().run();
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getId());
    }
}

class ComplexTest {
    public static void main(String[] args) {
        Complex a = new Complex(12, 17);
        System.out.println(a);
    }
}