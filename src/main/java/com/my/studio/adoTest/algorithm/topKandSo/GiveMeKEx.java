package com.my.studio.adoTest.algorithm.topKandSo;

import com.my.studio.adoTest.dataStructure.Tree.NodeSL;

public class GiveMeKEx {
    // 1.add necessary fields
    int[] stream;
    int k;
    NodeSL max;
    NodeSL min;
    NodeSL mid;
    int len;
    int flag = 1;
    int tag = (1 << 2);

    // 2.construct an instance of GiveMeK class
    public GiveMeKEx(int k, int[] stream) {
        this.stream = stream;
        this.k = k;
        for (int v : stream) {
            add(v, mid);
        }
    }

    private void add(int value, NodeSL node) {
        if (null == node) {
            mid = new NodeSL(value);
        } else {
            /*if (node.getValue().compareTo(value) > 0) {
                // add()
            }*/
        }
        len++;
    }

    public static void main(String[] args) {
        int[] s = {7, 3, 5, 8};
        int k =2;
        GiveMeKEx giveMeK = new GiveMeKEx(k, s);
    }

}
