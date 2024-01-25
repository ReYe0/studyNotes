package com.study.example.juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class CreateThreadPollThreadFactoryDemo {
    public static final int SLEEP_GAP = 1000;

    static class TargetTask implements Runnable {
        static AtomicInteger taskNo = new AtomicInteger(1);
        String taskName;

        public TargetTask() {
            taskName = "task-" + taskNo;
            taskNo.incrementAndGet();
        }

        @Override
        public void run() {

            System.out.println(Thread.currentThread().getName() + ": " + taskName + " is doing...");

            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(taskName + " end...");
        }
    }

    static class SimpleThreadFactory implements ThreadFactory {
        static AtomicInteger threadNo = new AtomicInteger(1);



        @Override
        public Thread newThread(Runnable task) {
            String threadName = "simpleThread-" + threadNo;
            System.out.println("创建一条线程，名字是：" + threadName);
            threadNo.incrementAndGet();
            Thread thread = new Thread(task, threadName);
            thread.setDaemon(true);
            return thread;
        }

    }
//    public static void main(String[] args) throws InterruptedException {
//        ExecutorService pool = Executors.newFixedThreadPool(2, new SimpleThreadFactory());
//        // ExecutorService pool=Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 5; i++) {
//            pool.submit(new TargetTask());
//        }
//        Thread.sleep(5000);
//        pool.shutdown();
//    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool=new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2)){
            @Override
            protected void terminated()
            {
                System.out.println("调度器已停止...");
            }
            @Override
            protected void beforeExecute(Thread t,Runnable target)
            {
                System.out.println("前钩执行...");
                super.beforeExecute(t, target);
            }
            @Override
            protected void afterExecute(Runnable target,Throwable t)
            {
                System.out.println("后钩执行...");
                super.afterExecute(target, t);
            }
        };
        for(int i=0;i<5;i++) {
            pool.execute(new TargetTask());
        }
        Thread.sleep(5000);
        pool.shutdown();

    }
}

