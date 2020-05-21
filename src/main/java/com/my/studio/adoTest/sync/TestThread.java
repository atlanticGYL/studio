package com.my.studio.adoTest.sync;

public class TestThread implements Runnable {

    private static Integer count;

    private int sw;

    public TestThread() {
        count = 0;
    }

    public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    synchronized(count) {
                        System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void  main(String args[]) {
        TestThread s1 = new TestThread();
        TestThread s2 = new TestThread();
        TestThread s3 = new TestThread();

        Thread t1 = new Thread(s1, "thread1");
        Thread t2 = new Thread(s2, "thread2");
        Thread t3 = new Thread(s3, "thread3");
        t1.start();
        t2.start();
        t3.start();
    }
}
