package com.my.studio.adoTest.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamApi {

    public static void main(String[] args) {
        listTest();
    }

    private static void listTest() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(0);
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);
        Stream<Integer> stream = integerList.stream();
    }

}
