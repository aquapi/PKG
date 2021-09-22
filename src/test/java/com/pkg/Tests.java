package com.pkg;

import java.util.concurrent.CompletableFuture;
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
        scope.async(9).then(a -> {
            System.out.println(Thread.currentThread().getName());
        }).handle();
        System.out.println(Thread.currentThread().getName());
    }
}

class ComplexTest {
    public static void main(String[] args) {
        Complex a = new Complex(12, 17);
        System.out.println(a);
    }
}

class Async {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> 9).thenAcceptAsync(System.out::println).handleAsync((x, y) -> {
            if (y != null)
                y.printStackTrace();
            return x;
        });
        System.out.println("Done");
    }
}