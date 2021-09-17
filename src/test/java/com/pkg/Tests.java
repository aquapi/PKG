package com.pkg;

import java.util.function.Consumer;

import com.pkg.async.AsyncScope;
import com.pkg.time.Timer;

class TimerTest {
    public static void main(String[] args) {
        new Timer().addTask(2000, (a) -> System.out.println(a)).addTask(2000, System.out::println)
                .addTask(3000, System.out::println).start();
    }
}

class AsyncTest extends AsyncScope {
    public static void main(String[] args) {
        scope.async(9).then((Consumer<Integer>) System.out::println).handle(System.err::println);
        System.out.println("Done");
    }
}