package com.my.studio.adoTest.jdk.javaSpi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class TestMain {
    public static void main(String[] args) {
        ServiceLoader<JavaSpi> s = ServiceLoader.load(JavaSpi.class);
        Iterator<JavaSpi> iterator = s.iterator();
        while (iterator.hasNext()) {
            JavaSpi spiTest =  iterator.next();
            spiTest.print();
        }
    }
}
