package com.pkg.util.function;

public interface ExecutableFunction {

    @FunctionalInterface
    public static interface EF1 {
        /**
         * @param x
         * @since 1.2
         */
        public void $(Object x);
    }

    @FunctionalInterface
    public static interface EF2 {
        /**
         * @param x
         * @param y
         * @since 1.2
         */
        public void $(Object x, Object[] y);
    }

    @FunctionalInterface
    public static interface EF3 {
        /**
         * @param x
         * @param y
         * @param z
         * @since 1.2
         */
        public void $(Object x, Object[] y, Class<?> z);
    }

    @FunctionalInterface
    public static interface EF4 {
        /**
         * @param a
         * @since 1.9
         */
        public void $(Object... a);
    }
}
