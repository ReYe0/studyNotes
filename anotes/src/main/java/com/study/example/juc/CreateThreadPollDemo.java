package com.study.example.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
/*
 * newSingleThreadExecutor创建“单线程化线程池”
 */
@SuppressWarnings("all")
public class CreateThreadPollDemo {
    public static final int SLEEP_GAP=1000;
    static class TargetTask implements Runnable{
        static AtomicInteger taskNo=new AtomicInteger(1);
        private String taskName;
        public TargetTask()
        {
            taskName="task-"+taskNo;
            taskNo.incrementAndGet();
        }
        @Override
        public void run()
        {
            System.out.println("task:"+taskName+" is doing...");
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task:"+taskName+" end...");
        }
    }
    public static void main(String[] args) {
        ExecutorService pool=Executors.newSingleThreadExecutor();
        for(int i=0;i<3;i++)
        {
            pool.execute(new TargetTask());
            pool.submit(new TargetTask());
        }
        pool.shutdown();
    }

}

