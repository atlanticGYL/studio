package com.my.studio.adoTest.algorithm.aboutTree;

import java.util.HashMap;
import java.util.Map;

public class MaxPerLevel {

    private Map<Integer, Integer> valMap = new HashMap<>();

    /**
     * @author yaobo
     * 二叉树的先序中序后序排序
     */
    public Node init() {//注意必须逆序建立，先建立子节点，再逆序往上建立，因为非叶子结点会使用到下面的节点，而初始化是按顺序初始化的，不逆序建立会报错
        Node L = new Node(11, null, null);
        Node K = new Node(10, null, null);
        Node J = new Node(8, K, L);
        Node H = new Node(4, null, null);
        Node G = new Node(2, null, null);
        Node F = new Node(7, null, J);
        Node E = new Node(5, H, null);
        Node D = new Node(1, null, G);
        Node C = new Node(9, F, null);
        Node B = new Node(3, D, E);
        Node A = new Node(6, B, C);
        return A;   //返回根节点
    }

    public void printNode(Node node, int level, String lmr) {
        System.out.println(lmr + level + ":" + node.getData());
        mapPut(level, node.getData());
    }

    public void mapPut(int level, int val) {

    }

    public void theFirstTraversal(Node root, int level, String lmr) {  //先序遍历
        level++;
        printNode(root, level, lmr);
        if (root.getLeftNode() != null) {  //使用递归进行遍历左孩子
            theFirstTraversal(root.getLeftNode(), level, "L");
        }
        if (root.getRightNode() != null) {  //递归遍历右孩子
            theFirstTraversal(root.getRightNode(), level, "R");
        }
        level--;
    }

    public static void main(String[] args) {
        MaxPerLevel tree = new MaxPerLevel();
        Node root = tree.init();
        System.out.println("先序遍历");
        tree.theFirstTraversal(root, -1, "M");
    }
}

class Node {
    private int data;
    private Node leftNode;
    private Node rightNode;

    public Node(int data, Node leftNode, Node rightNode) {
        this.data = data;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
}