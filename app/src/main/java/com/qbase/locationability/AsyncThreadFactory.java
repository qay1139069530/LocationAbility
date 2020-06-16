package com.qbase.locationability;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncThreadFactory implements ThreadFactory {
    private final ThreadGroup threadGroup;
    private final AtomicInteger atomicInteger = new AtomicInteger(1);
    private final String name;

    public AsyncThreadFactory(String paramString) {
        Object localObject = System.getSecurityManager();
        if (localObject != null) {
            localObject = ((SecurityManager) localObject).getThreadGroup();
        } else {
            localObject = Thread.currentThread().getThreadGroup();
        }
        this.threadGroup = ((ThreadGroup) localObject);
        localObject = new StringBuilder();
        ((StringBuilder) localObject).append(paramString);
        ((StringBuilder) localObject).append("-pool-thread-");
        this.name = ((StringBuilder) localObject).toString();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(atomicInteger.getAndIncrement());
        Thread thread = new Thread(threadGroup, runnable, stringBuilder.toString(), 0L);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        return thread;
    }
}
