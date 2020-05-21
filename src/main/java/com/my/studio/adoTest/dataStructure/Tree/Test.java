package com.my.studio.adoTest.dataStructure.Tree;

import java.util.Comparator;
import java.util.Random;

public class Test {
    private static int count = 20;


    public static void main(String[] args) {
        dlTreeTest();
    }

    private static void dlTreeTest() {
        Random random = new Random();

        SearchDLTree searchDLTree = new SearchDLTree(110, Comparator.naturalOrder(), true);
        while (count > 0) {
            int t = random.nextInt(300);
            System.out.println(t);
            searchDLTree.add(t);
            count--;
        }
        System.out.println(searchDLTree);
    }
}
