package com.my.exam;

import java.util.ArrayList;
import java.util.LinkedList;

public class test4 {
    public static void main(String[] args) {
        ArrayList<String> l1 = new ArrayList<String>();
        ArrayList<Integer> l2 = new ArrayList<Integer>();
        LinkedList<String> l3 = new LinkedList<String>();
        l1.add("1");
        l2.add(1);
        l3.add("1");
        System.out.println(l1.get(0).getClass());
        System.out.println(l2.get(0).getClass());
        System.out.println(l3.get(0).getClass());
        System.out.println(l1.getClass());
        System.out.println(l2.getClass());
        System.out.println(l3.getClass());
        // true
        System.out.println(l1.getClass() == l2.getClass());
        // false
        System.out.println((Class<?>)l1.getClass() == (Class<?>)l3.getClass());


    }
}
