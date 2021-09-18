package com.pkg;

import java.util.function.Consumer;

import com.pkg.async.AsyncScope;
import com.pkg.struct.Block;
import com.pkg.time.Timer;

class TimerTest {
    public static void main(String[] args) {
        new Timer().addTask(2000, (a) -> System.out.println(a)).addTask(2000, System.out::println)
                .addTask(3000, System.out::println).start();
    }
}

class AsyncTest extends AsyncScope {
    public static void main(String[] args) {
        scope.async(9).then((Consumer<Integer>) System.out::println).handle();
        System.out.println("Done");
    }
}

class BlockTest {
    public static void main(String[] args) {
        Block i = new Block(9);
        i.addConnect(new Block(10));
        i.addConnect(new Block(15));
        i.connects()[1].addConnect(new Block(16));
        System.out.println(i);
    }
}