package com.pkg.math;

import com.pkg.def.Static;

public final class Complex extends Number {
    private double real, imag;

    /**
     * @param real
     * @param imag Constructs new Complex number
     * @since 1.8
     */

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    /**
     * @param imag Constructs new Complex number with only imag
     * @since 1.8
     */

    public Complex(double imag) {
        this(0, imag);
    }

    /**
     * @param c + current number
     * @since 1.8
     */

    public void add(Complex c) {
        this.real += c.real;
        this.imag += c.imag;
    }

    /**
     * @param x
     * @param y
     * @return x + y
     * @since 1.8
     */

    public static Complex add(Complex x, Complex y) {
        return new Complex(x.real + y.real, x.imag + y.imag);
    }

    /**
     * @param c change current number by -c
     * @since 1.8
     */

    public void minus(Complex c) {
        this.real -= c.real;
        this.imag -= c.imag;
    }

    /**
     * @param x
     * @param y
     * @return x - y
     * @since 1.8
     */

    public static Complex minus(Complex x, Complex y) {
        return new Complex(x.real - y.real, x.imag - y.imag);
    }

    /**
     * @param c * current number
     * @since 1.8
     */

    public void multiply(Complex c) {
        this.real = this.real * c.real - this.imag * c.imag;
        this.imag = this.real * c.imag + this.imag * c.real;
    }

    /**
     * @param x
     * @param y
     * @return x * y
     * @since 1.8
     */

    public static Complex multiply(Complex x, Complex y) {
        double new_real = x.real * y.real - x.imag * y.imag;
        double new_imag = x.real * y.imag + x.imag * y.real;
        return new Complex(new_real, new_imag);
    }

    /**
     * @param c divide current number by c
     * @since 1.8
     */

    public void divide(Complex c) {
        this.real = (this.real * c.real + this.imag * c.imag) / (c.real * c.real + c.imag * c.imag);
        this.imag = (this.imag * c.real - this.real * c.imag) / (c.real * c.real + c.imag * c.imag);
    }

    /**
     * @param x
     * @param y
     * @return x / y
     * @since 1.8
     */

    public static Complex divide(Complex x, Complex y) {
        double new_real = (x.real * y.real + x.imag * y.imag) / (y.real * y.real + y.imag * y.imag);
        double new_imag = (x.imag * y.real - x.real * y.imag) / (y.real * y.real + y.imag * y.imag);
        return new Complex(new_real, new_imag);
    }

    /**
     * @param c
     * @return true if current number is equavalent to c
     * @since 1.8
     */

    public boolean equals(Complex c) {
        return c.real == this.real && c.imag == this.imag ? true : false;
    }

    /**
     * @param x
     * @param y
     * @return true if x == y
     * @since 1.8
     */

    public static boolean compare(Complex x, Complex y) {
        return x.real == y.real && x.imag == y.imag ? true : false;
    }

    /**
     * @param s
     * @return new Complex initialized to the value represented by the specified
     *         String
     * @see Complex#value()
     * @since 1.9
     */

    public static Complex parse(String s) {
        String k = new StringBuffer(s).toString();
        if (s == "i")
            return new Complex(1);
        String[] temp = {};
        double res_real, res_imag;
        boolean real_nega = false, imag_nega = false;
        String realString = null;
        String imagString = null;
        if (s.charAt(0) == '-') {
            s = s.substring(1);
            real_nega = true;
        }
        if (Static.isDigit(s.charAt(0))) {
            if (s.contains("i")) {
                temp = new String[2];
                temp[0] = "0";
                temp[1] = s.split("i")[0];
                imag_nega = real_nega;
                real_nega = false;
            } else {
                temp = new String[2];
                temp[0] = s;
                temp[1] = "0";
            }
        }
        if (s.contains("+")) {
            temp = s.split("\\+");
        } else if (s.contains("-")) {
            temp = s.split("-");
            imag_nega = true;
        }
        if (temp[0].contains("i")) {
            throw new NumberFormatException("For input string: " + k);
        } else {
            realString = temp[0];
            imagString = temp[1].trim().split("i")[0];
        }
        res_real = Double.parseDouble(realString);
        res_imag = Double.parseDouble(imagString);
        if (real_nega)
            res_real = -res_real;
        if (imag_nega)
            res_imag = -res_imag;
        return new Complex(res_real, res_imag);
    }

    /**
     * @return complex value in string
     * @since 1.8
     */

    public String toString() {
        if (this.imag == this.real && this.real == 0.0d) {
            return "0";
        }
        if (this.imag == 0.0d) {
            return this.real + "";
        }
        if (this.real == 0.0d) {
            return this.imag + "i";
        }
        if (this.imag < 0.0d) {
            return this.real + " - " + -this.imag + "i";
        }
        return this.real + " + " + this.imag + "i";
    }

    /**
     * Number method
     * 
     * @since 1.8
     */

    @Override
    public int intValue() {
        return (int) (this.real);
    }

    /**
     * Number method
     * 
     * @since 1.8
     */

    @Override
    public long longValue() {
        return (long) this.real;
    }

    /**
     * Number method
     * 
     * @since 1.8
     */

    @Override
    public float floatValue() {
        return (float) this.real;
    }

    /**
     * Number method
     * 
     * @since 1.8
     */

    @Override
    public double doubleValue() {
        return this.real;
    }
}
