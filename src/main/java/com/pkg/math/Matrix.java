package com.pkg.math;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

public class Matrix implements Serializable {
    private LinkedList<double[]> matrix = new LinkedList<>();
    private final int rowSize;
    private final int chms;

    /**
     * @param vector in this matrix
     * @since 2.1
     */

    public Matrix(double... vector) {
        this.matrix.add(vector);
        rowSize = vector.length;
        chms = 0;
        this.fix();
    }

    /**
     * @param matrix
     * @since 2.1
     */

    public Matrix(double[]... matrix) {
        int max = Integer.MIN_VALUE;
        int chms_index = 0;
        for (double[] k : matrix) {
            if (max < k.length) {
                max = k.length;
                chms_index = this.matrix.size();
            }
            this.matrix.add(k);
        }
        chms = chms_index;
        rowSize = max;
        this.fix();
    }

    /**
     * @return this matrix in <code>LinkedList&#60double[]&#62</code>
     * @since 2.1
     */

    public LinkedList<double[]> value() {
        return matrix;
    }

    /**
     * @param index
     * @return row by index
     * @throws IndexOutOfBoundsException
     * @since 2.0
     */

    public double[] rows(int index) {
        return matrix.get(index);
    }

    /**
     * @param m + this matrix
     * @since 2.1
     */

    public void add(Matrix m) {
        if (m.rowSize != this.rowSize && m.chms == this.chms || m.matrix.size() != this.matrix.size())
            throw new IllegalArgumentException();
        for (int i = 0; i < matrix.size(); i++) {
            double[] p = matrix.get(i);
            for (int j = 0; j < p.length; j++) {
                this.matrix.get(i)[j] += m.matrix.get(i)[j];
            }
        }
        this.fix();
    }

    /**
     * @param n * this matrix
     * @since 2.1
     */

    public void multiply(int n) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                this.matrix.get(i)[j] *= n;
            }
        }
    }

    /**
     * tranpose this matrix
     * 
     * @since 2.1
     */

    public void tranpose() {
        LinkedList<double[]> tranpose = new LinkedList<>();
        for (int i = 0; i < rowSize; i++) {
            tranpose.add(new double[matrix.size()]);
        }
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                tranpose.get(j)[i] = matrix.get(i)[j];
            }
        }
        matrix = tranpose;
    }

    /**
     * fix this matrix
     * 
     * <pre>
     * <blockquote>
     * Matrix matrix = new Matrix(new double[]{2.0, 3.0}, new double[]{1.0, 4.0, 5.0});
     * System.out.println(matrix.toString()) // ({2.0, 3.0}, \n {1.0, 4.0, 5.0})
     * matrix.fix();
     * System.out.println(matrix.toString()) // ({2.0, 3.0, 0.0}, \n {1.0, 4.0, 5.0})
     * </blockquote>
     * </pre>
     * 
     * @since 2.1
     */

    private void fix() {
        LinkedList<double[]> fixed = new LinkedList<>();
        for (int i = 0; i < matrix.size(); i++) {
            fixed.add(new double[rowSize]);
        }
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).length; j++) {
                fixed.get(i)[j] = matrix.get(i)[j];
            }
        }
        matrix = fixed;
    }

    /**
     * @param m * this matrix
     * @since 2.1
     */

    public void multiply(Matrix m) {
        if (m.rowSize != matrix.size())
            return;
        LinkedList<double[]> result = new LinkedList<>();
        for (int i = 0; i < rowSize; i++) {
            result.add(new double[m.matrix.size()]);
        }
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < m.matrix.size(); j++) {
                result.get(i)[j] = 0;
                for (int k = 0; k < matrix.size(); k++) {
                    result.get(i)[j] += m.matrix.get(i)[k] * matrix.get(k)[j];
                }
            }
        }
        matrix = result;
    }

    @Override
    public String toString() {
        String res = "(";
        for (int i = 0; i < matrix.size(); i++) {
            double[] line = matrix.get(i);
            res += "{";
            for (double j : line) {
                res += j + ", ";
            }
            StringBuffer fix = new StringBuffer(res);
            fix.delete(fix.length() - 2, fix.length());
            res = fix.toString();
            res += "}" + ", " + "\n ";
        }
        StringBuffer fix = new StringBuffer(res);
        fix.delete(fix.length() - 4, fix.length());
        res = fix.toString();
        res += ")";
        return res;
    }

    /**
     * Serial Version
     * 
     * @since 2.1
     */

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @param o matrix to compare
     * @return true if this matrix equals to o
     * @since 2.4
     */

    public boolean equals(Matrix o) {
        for (int i = 0; i < o.matrix.size(); i++) {
            if (!Arrays.equals(matrix.get(i), o.matrix.get(i))) 
                return false; 
        }
        return true;
    }
}
