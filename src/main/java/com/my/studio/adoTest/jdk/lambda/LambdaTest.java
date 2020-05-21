package com.my.studio.adoTest.jdk.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LambdaTest {

    private ILambda1 iLambda1;
    private ILambda3 iLambda3;

    public LambdaTest(ILambda1 iLambda1) {
        this.iLambda1 = iLambda1;
    }

    public void test31() {
        iLambda1.lambdaTest1();
    }

    /*public LambdaTest(ILambda3 iLambda3) {
        this.iLambda3 = iLambda3;
    }

    public void test33() {
        iLambda3.lambdaTest3();
    }*/

    public static void test0() {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1,2,3,4,5);
        //lambda表达式 方法引用
        list.forEach(System.out::println);
        list.forEach(element -> {
            if (element % 2 == 0) {
                System.out.println(element);
            }
        });
    }

    public static void test1() {
        ILambda1 iLambda1 = () -> System.out.println("lambdaTest1");
        iLambda1.lambdaTest1();
        iLambda1.lambdaTest2();
    }

    public static void test2() {
        ILambda2 iLambda2 = (() -> "ILambda2.lambdaTest2");
        System.out.println(iLambda2.lambdaTest2());
    }

    public static void test3() {
        new LambdaTest(() -> System.out.println("lambdaTest31")).test31();
    }

    public static void main(String[] args) {
        test3();
    }
}

interface ILambda1 {
    void lambdaTest1();
    default void lambdaTest2() {
        System.out.println("ILambda1.lambdaTest2");
    }
}

interface ILambda2 {
    String lambdaTest2();
}

interface ILambda3 {
    void lambdaTest3();
}