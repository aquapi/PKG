package com.pkg;

import java.util.function.Consumer;

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
        scope.async(9).then((Consumer<Integer>) System.out::println);
        System.out.println("Done");
    }
}

class ComplexTest {
    public static void main(String[] args) {
        Complex a = new Complex(12, 17);
        System.out.println(a);
    }
}
