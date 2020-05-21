package com.my.studio.adoTest.Threadpool;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolTest {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
        // forkJoinPool.execute();
    }
}
