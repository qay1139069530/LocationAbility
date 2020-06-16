package com.qbase.locationability;

import android.os.Looper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AsyncExec {

    public enum ThreadType {
        IO, NETWORK, SEQUENCE, EVENT
    }

    private static final int CODE_THREAD_NUMBER = 3;
    private static final int THREAD_NUMBER = 5;
    private static final int KEEP_ALIVE_TIME = 60;

    private static Map<ThreadType, ExecutorService> executorMap;

    private synchronized static void init() {
        if (null == executorMap) {
            Map<ThreadType, ExecutorService> map = new HashMap<>(4);
            ThreadPoolExecutor ioExecutor = new ThreadPoolExecutor(
                    CODE_THREAD_NUMBER, THREAD_NUMBER, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(), new AsyncThreadFactory("io"));
            ioExecutor.allowCoreThreadTimeOut(true);

            ThreadPoolExecutor networkExecutor = new ThreadPoolExecutor(
                    CODE_THREAD_NUMBER, THREAD_NUMBER, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(), new AsyncThreadFactory("net"));
            networkExecutor.allowCoreThreadTimeOut(true);

            ThreadPoolExecutor seqExecutor = new ThreadPoolExecutor(
                    0, 1, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(), new AsyncThreadFactory("seq"));
            seqExecutor.allowCoreThreadTimeOut(true);

            ThreadPoolExecutor eventExecutor = new ThreadPoolExecutor(
                    0, 1, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(), new AsyncThreadFactory("event"));
            networkExecutor.allowCoreThreadTimeOut(true);

            map.put(ThreadType.IO, ioExecutor);
            map.put(ThreadType.NETWORK, networkExecutor);
            map.put(ThreadType.SEQUENCE, seqExecutor);
            map.put(ThreadType.EVENT, eventExecutor);
            executorMap = map;
        }
    }

    static {
        init();
    }

    public static ExecutorService getExecutor(ThreadType threadType) {
        return executorMap.get(threadType);
    }

    private static boolean isMainThread() {
        Looper mainLooper = Looper.getMainLooper();
        return mainLooper != null && Thread.currentThread() == mainLooper.getThread();
    }

    public static void submit(Runnable runnable, ThreadType threadType, boolean runInSameBackgroundThread) {
        if (null == runnable) {
            return;
        }

        if(runInSameBackgroundThread && isMainThread()){
            TaskWrapper taskWrapper = new TaskWrapper(runnable);
            taskWrapper.run();
        }else{
            ExecutorService executorService = executorMap.get(threadType);
            if(executorService!=null){
                executorService.execute(new TaskWrapper(runnable));
            }
        }
    }

    public static void submitIO(Runnable runnable){
        submit(runnable, ThreadType.IO,false);
    }

    public static void submitNet(Runnable runnable){
        submit(runnable, ThreadType.NETWORK,false);
    }

    public static void submitSeq(Runnable runnable){
        submit(runnable, ThreadType.SEQUENCE,false);
    }

    public static void submitEvent(Runnable runnable){
        submit(runnable, ThreadType.EVENT,false);
    }
}



