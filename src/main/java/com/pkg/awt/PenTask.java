package com.pkg.awt;

public interface PenTask { 
    /**
     * @param pen target pen
     * @since 1.8
     */

    public void doTask(Pen pen);

    /**
     * @param time to repeat
     * @param pen  target pen
     * @since 1.8
     */

    public default void repeat(int time, Pen pen) {
        for (int i = 0; i < time; i++) {
            this.doTask(pen);
        }
    }
}
