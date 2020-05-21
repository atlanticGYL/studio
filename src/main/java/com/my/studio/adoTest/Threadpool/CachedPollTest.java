package com.my.studio.adoTest.Threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedPollTest {
    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    }
}
