package com.pkg.time;

import java.util.LinkedList;

import com.pkg.util.Pair;

public class Timer {
    private final long endTime;
    private final LinkedList<Pair<Long, Task>> tasks;

    public Timer(long endTime) {
        this.endTime = endTime;
        tasks = new LinkedList<>();
    }

    public Timer() {
        this(Long.MAX_VALUE);
    }

    public long getEnds() {
        return endTime;
    }

    public Timer addTask(long delay, Task t) {
        tasks.add(new Pair<Long, Task>(delay, t));
        return this;
    }

    public Timer removeTask(int index) {
        tasks.remove(index);
        return this;
    }

    private void setTime(Task t, long delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                t.execute(delay); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void start() {
        long currentTime = 0;
        for (Pair<Long, Task> task : tasks) {
            if (currentTime >= endTime)
                break;
            currentTime += task.getFirst();
            setTime(task.getLast(), currentTime);
        }
    }
}
