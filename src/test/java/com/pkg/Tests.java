package com.pkg;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.pkg.async.AsyncScope;
import com.pkg.awt.Pen;
import com.pkg.io.Folder;
import com.pkg.math.Complex;
import com.pkg.math.Matrix;
import com.pkg.time.Timer;

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
        System.out.println(a.compareTo(b));
    }
}

class FileTest {
    public static void main(String[] args) throws IOException {
        Folder f = new Folder("src");
        System.out.println(f);
    }
}

class MatrixTest {
    public static void main(String[] args) {
        Matrix x = new Matrix(
            new double[]{9, 3, 5},
            new double[]{3, 7, 6}
        );
        System.out.println(x);
    }
}

class PenTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pen");
        frame.setVisible(true);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Pen p = new Pen(50, 50, 600, 600);
        p.setColor(Color.RED);
        p.forward(200);
        p.setColor(Color.BLUE);
        p.rotate(90);
        p.forward(200);
        frame.add(p);
    }
}
