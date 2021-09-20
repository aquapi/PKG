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
        Block b = new Block(9);
        b.addConnect(new Block(16));
        b.addConnect(new Block(12));
        System.out.println(b);
    }
}