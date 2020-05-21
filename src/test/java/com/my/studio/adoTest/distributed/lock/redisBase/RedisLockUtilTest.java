package com.my.studio.adoTest.distributed.lock.redisBase;

public class RedisLockUtilTest {
    public static void main(String args[]) {
        ReEntraintThread t1 = new ReEntraintThread();
        ReEntraintThread t2 = new ReEntraintThread();
        t1.start();
        t2.start();
    }

    static class ReEntraintThread extends Thread {
        public void run() {
            reEntraintTest();
        }
        public void reEntraintTest() {
            boolean flag = RedisLockUtil.lock2("test1", 5);
            System.out.println(Thread.currentThread().getName() + "-" + flag);
            if (flag) {
                reEntraintTest();
            }
            RedisLockUtil.unLock2("test1");
        }
    }
}
