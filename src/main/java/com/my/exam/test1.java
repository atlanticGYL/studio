package com.my.exam;

public class test1 {
    static class A {
        static {
            System.out.print("A");
        }
        public A() {
            System.out.print("a");
        }
        void say() {
            System.out.print("1");
        }
    }
    static class B extends A {
        static {
            System.out.print("B");
        }
        public B() {
            System.out.print("b");
        }
        void say() {
            System.out.print("2");
        }
    }

        public static void main(String[] args) {
            A ab = new B();
            ab.say();
            ab = new B();
            ab.say();
        }

}
