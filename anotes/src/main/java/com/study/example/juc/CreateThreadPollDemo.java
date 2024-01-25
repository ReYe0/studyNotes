package com.study.example.juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
/*
 * newSingleThreadExecutor创建“单线程化线程池”
 * 特点：
    单线程化的线程池中的任务是按照提交的次序顺序执行的
    只有一个线程的线程池
    池中的唯一线程的存活时间是无限的
    当池中的唯一线程正繁忙时，新提交的任务实例会进入内部的阻塞队列中，并且其阻塞队列是无界的
    适用场景：任务按照提交次序，一个任务一个任务地逐个执行的场景
    *
    * newFixedThreadPool创建“固定数量的线程池
    * 特点：

    如果线程数没有达到“固定数量”，每次提交一个任务线程池内就创建一个新线程，直到线程达到线程池固定的数量
    线程池的大小一旦达到“固定数量”就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程
    在接收异步任务的执行目标实例时，如果池中的所有线程均在繁忙状态，新任务会进入阻塞队列中（无界的阻塞队列）
    适用场景：

    需要任务长期执行的场景
    CPU密集型任务
    缺点：

    内部使用无界队列来存放排队任务，当大量任务超过线程池最大容量需要处理时，队列无限增大，使服务器资源迅速耗尽
    *
    * newCachedThreadPool创建“可缓存线程池”
    * 特点：

    在接收新的异步任务target执行目标实例时，如果池内所有线程繁忙，此线程池就会添加新线程来处理任务
    线程池不会对线程池大小进行限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小
    如果部分线程空闲，也就是存量线程的数量超过了处理任务数量，就会回收空闲（60秒不执行任务）线程
    适用场景：

    需要快速处理突发性强、耗时较短的任务场景，如Netty的NIO处理场景、REST API接口的瞬时削峰场景
    缺点：

    线程池没有最大线程数量限制，如果大量的异步任务执行目标实例同时提交，可能会因创建线程过多而导致资源耗尽
    *
    * newScheduledThreadPool创建“可调度线程池”
    * 延时性
    周期性
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
    public static void main(String[] args) throws InterruptedException {
//        ExecutorService pool=Executors.newSingleThreadExecutor();//唯一线程
//        ExecutorService pool=Executors.newFixedThreadPool(3);//创建含有3个线程的线程池
//        ExecutorService pool=Executors.newCachedThreadPool();
//        for(int i=0;i<3;i++)
//        {
//            pool.execute(new TargetTask());
//            pool.submit(new TargetTask());
//        }
//        pool.shutdown();
        ScheduledExecutorService pool=Executors.newScheduledThreadPool(2);
        Future<Integer> future=pool.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return 123;
            }

        });
        try {
            Integer result=future.get();
            System.out.println("result:"+result);//123
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        pool.shutdown();
    }

}

