package com.pkg;

import com.pkg.async.AsyncScope;
import com.pkg.math.Complex;
import com.pkg.time.Timer;
import com.pkg.def.Maths;

class TimerTest {
    public static void main(String[] args) {
        new Timer().addTask(2000, System.out::println).addTask(2000, System.out::println) // 2000 -> first task, 4000 ->
                                                                                          // second task, 7000 -> third
                                                                                          // task
                .addTask(3000, System.out::println).start();
    }
}

class AsyncTest extends AsyncScope {
    public void run() { // Asynchronous function
        new Thread(() -> {
            System.out.println(await(resolve(9)));
            resolve(9).then(a -> {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getId());
                System.out.println(Thread.currentThread().isAlive());
            }).handle();
        }).start();
    }

    public static void main(String[] args) {
        new AsyncTest().run();
        System.out.println(Thread.currentThread().getName());
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

class MathTest {
    public static void main(String[] args) {
        System.out.println(Maths.acot(Maths.cot(19)));
    }
}