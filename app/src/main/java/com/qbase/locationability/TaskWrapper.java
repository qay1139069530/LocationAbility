package com.qbase.locationability;

public class TaskWrapper implements Runnable {

    private Runnable runnable;

    public TaskWrapper(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        if (null != runnable) {
            runnable.run();
        }
    }
}
