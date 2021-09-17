package com.pkg.util.function;

import java.util.List;
import java.util.Set;

public interface ReturnFunction {

    @FunctionalInterface
    public static interface FI1 {
        /**
         * @param x
         * @return ANYTHING YOU WANT
         * @since 1.1
         */
        public Object[] $(Object[] x, Object y);
    }

    @FunctionalInterface
    public static interface FI2 {
        /**
         * @param x
         * @return ANYTHING YOU WANT
         * @since 1.1
         */
        public Object $(Object x);
    }

    @FunctionalInterface
    public static interface FI3 {
        /**
         * @param x
         * @param y
         * @return ANYTHING YOU WANT
         * @since 1.1
         */
        public Set<?> $(Set<?> x, Object y);
    }

    @FunctionalInterface
    public static interface FI4 {
        /**
         * @param x
         * @param y
         * @return ANYTHING YOU WANT
         * @since 1.1
         */
        public Object $(Object[] x, Object y);
    }

    @FunctionalInterface
    public static interface FI5 {
        /**
         * @param x
         * @param y
         * @return ANYTHING YOU WANT
         * @since 1.4
         */
        public Object $(Class<?> x, Object y);
    }

    @FunctionalInterface
    public static interface FI6 {
        /**
         * @param x
         * @param y
         * @return ANYTHING YOU WANT
         * @since 1.4
         */
        public Object $(Object[] x);
    }

    @FunctionalInterface
    public static interface FI7 {
        /**
         * @param x
         * @param y
         * @return ANYTHING YOU WANT
         * @since 1.4
         */
        public Object[] $(Object[] x, Object y, Object z);
    }

    @FunctionalInterface
    public static interface FI8 {
        /**
         * @param x
         * @param list
         * @return ANYTHING YOU WANT
         * @since 1.4
         */
        public List<?> $(List<?> list, Object x);
    }

    @FunctionalInterface
    public interface FI9 {
        /**
         * @param x
         * @param y
         * @return ANYTHING YOU WANT
         * @since 1.5
         */

        Object[] $(Object x, Object y);
    }
}
