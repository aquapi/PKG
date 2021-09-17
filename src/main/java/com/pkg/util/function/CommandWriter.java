package com.pkg.util.function;

@FunctionalInterface
public interface CommandWriter {
    /**
     * @since Extension 1.0
     */
    public void Writer();

    /**
     * @param time    how many times the command can be repeated?
     * @param command to repeat
     * @since 1.0
     */

    public default void repeat(int time) {
        for (int num = 0; num < time; num++) {
            this.Writer();
        }
    }
}
