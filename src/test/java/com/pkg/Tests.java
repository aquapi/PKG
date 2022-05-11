package com.pkg;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.pkg.awt.Pen;
import com.pkg.math.Complex;
import com.pkg.math.Fraction;
import com.pkg.time.Timer;

class TimerTest {
    public static void main(String[] args) {
        new Timer().addTask(2000, System.out::println).addTask(2000, System.out::println) // 2000 -> first task, 4000 ->
                                                                                          // second task, 7000 -> third
                                                                                          // task
                .addTask(3000, System.out::println).start();
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

class PenTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pen") {
            {
                setVisible(true);
                setSize(700, 700);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        };
        Pen p = new Pen();
        p.setColor(Color.RED);
        p.forward(200);
        p.setColor(Color.BLUE);
        p.rotate(90);
        p.forward(200);
        p.undo();
        frame.add(p);
    }
}

class FractionsTest {
    public static void main(String[] args) {
        Fraction x = new Fraction(9, 15);
        x.simplify();
        System.out.println(x);
    }
} 