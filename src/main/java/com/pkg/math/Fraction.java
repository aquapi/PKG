package com.pkg.math;

public class Fraction extends Number {
    private double numerator, denominator;

    /**
     * @param numerator 
     * @param denominator
     * @since 2.4
     */

    public Fraction(double numerator, double denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * @param denominator
     * @since 2.4
     */

    public Fraction(double denominator) {
        this(1, denominator);
    }

    /**
     * @since 2.4
     */

    public Fraction() {
        this(1);
    }

    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return numerator / denominator;
    }

    /**
     * @param a a fraction
     * @return the result
     * @since 2.4
     */

    public Fraction add(Fraction a) {
        return new Fraction((a.numerator * denominator) + (a.denominator * numerator), denominator * a.denominator);
    }

    /**
     * @param a a fraction
     * @return the result
     * @since 2.4
     */

    public Fraction subtract(Fraction a) {
        return add(new Fraction(-a.numerator, a.denominator));
    }

    /**
     * @param a a fraction
     * @return the result
     * @since 2.4
     */

    public Fraction multiply(Fraction a) {
        return new Fraction(a.numerator * numerator, a.denominator * denominator);
    }

    /**
     * @param a a fraction
     * @return the result
     * @since 2.4
     */

    public Fraction divide(Fraction a) {
        return new Fraction(numerator * a.denominator, denominator * a.numerator);
    }

    public String toString() {
        return "(" + numerator + " / " + denominator + ")";
    }

    /**
     * @param a
     * @param b
     * @return the greatest common divisor
     */

    private double _gcd_(double a, double b) {
        return (b == 0) ? a : _gcd_(b, a % b);
    }

    /**
     * @apiNote simplify the fraction
     * @since 2.4
     */

    public void simplify() {
        double dv = _gcd_(numerator, denominator);
        numerator /= dv;
        denominator /= dv;
    }
}
