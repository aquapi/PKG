package com.pkg.time;

public interface Task {
    /**
     * @param currentTime the current time of the task
     * @since 2.4
     */

    public void execute(long currentTime);
}
