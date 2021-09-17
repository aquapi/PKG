package com.pkg.util.function;

@SuppressWarnings("unchecked")
public interface Function {

    @FunctionalInterface
    public static interface A<X> {

        /**
         * @param <X> args type
         * @param args 
         * @since 1.9
         */

        public void $(X... args);
    }

    @FunctionalInterface
    public static interface B<R, X> {

        /**
         * @param <X>  args type
         * @param args
         * @return result with type R
         * @since 1.9
         */

        public R $(X... args);
    }

    @FunctionalInterface
    public static interface C<X, Y> {

        /**
         * @param <X> normal parameter
         * @param <Y> args type
         * @param param 
         * @param args
         * @since 1.9
         */

        public void $(X param, Y... args);
    }

    @FunctionalInterface
    public static interface D<R, X, Y> {

        /**
         * @param <X> normal parameter
         * @param <Y> args type
         * @param param
         * @param args
         * @return result with type R
         * @since 1.9
         */

        public R $(X param, Y... args);
    }
}
