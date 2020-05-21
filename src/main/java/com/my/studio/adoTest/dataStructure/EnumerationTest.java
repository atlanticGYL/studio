package com.my.studio.adoTest.dataStructure;

import java.util.*;

public class EnumerationTest {

    public static void main(String args[]){
        // Vector 定义泛型, Enumeration 可指定相同<E> 或 Enumeration
        Enumeration<String> days;
        Vector<String> dayNames = new Vector<String>();
        dayNames.add("Sunday");
        dayNames.add("Monday");
        dayNames.add("Tuesday");
        dayNames.add("Wednesday");
        dayNames.add("Thursday");
        dayNames.add("Friday");
        dayNames.add("Saturday");
        days = dayNames.elements();
        // Vector 基于数组, 输出内容有序
        while (days.hasMoreElements()){
            System.out.println(days.nextElement());
        }

        // Properties 无泛型定义, 继承 Hashtable<Object,Object>, Enumeration 只能定义为 Enumeration<Object> 或 Enumeration
        Enumeration<Object> enumeration;
        Properties properties = new Properties();
        properties.setProperty("1", "1");
        properties.setProperty("2", "2");
        properties.setProperty("3", "3");
        enumeration = properties.elements();
        // Properties 基于 Hashtable，按散列顺序, 输出内容无序
        while (enumeration.hasMoreElements()){
            System.out.println(enumeration.nextElement());
        }
    }
}
