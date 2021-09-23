package com.pkg;

import com.pkg.async.AsyncScope;
import com.pkg.math.Complex;
import com.pkg.time.Timer;

class TimerTest {
    public static void main(String[] args) {
        new Timer().addTask(2000, System.out::println).addTask(2000, System.out::println) // 2000 -> first task, 4000 -> second task, 7000 -> third task
                .addTask(3000, System.out::println).start();
    }
}

class AsyncTest extends AsyncScope {
    public void run() { // Asynchronous function
        async(9).then(a -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getId());
            return a;
        }).then(a -> {
            System.out.println(a);
        }).handle();
    }
    public static void main(String[] args) {
        new AsyncTest().run(); // Should not use with synchronous statement
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getId());
        // Cause unpredictable result
    }
}

class ComplexTest {
    public static void main(String[] args) {
        Complex a = new Complex(12, 17); // Complex number constructor
        System.out.println(a);
        Complex b = Complex.parse("15 + 9i"); // parse a string to Complex
        System.out.println(b);
    }
}