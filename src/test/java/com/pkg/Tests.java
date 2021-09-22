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
    public static void main(String[] args) {
        scope.async(() -> {
            System.out.println(9);
            return null;
        }).handle();
        System.out.println("Done");
    }
}

class ComplexTest {
    public static void main(String[] args) {
        Complex a = new Complex(12, 17);
        System.out.println(a);
    }
}