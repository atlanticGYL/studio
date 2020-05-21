package com.my.exam;

public class test5 {
    private void test() {
        abstract class TestInner {
            public void pt() {
                System.out.println("pt");
            }
            abstract void pt2();
            private String name;
            public int age;
        }
        class TestInnerSub extends TestInner {
            @Override
            void pt2() {
                System.out.println("p2");
                pt();
            }
        }
        TestInner testInner = new TestInnerSub();
        testInner.pt2();
    }

    public static void main(String[] args) {
        test5 test5 = new test5();
        test5.test();
    }
}
