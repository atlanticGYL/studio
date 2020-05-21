package com.my.studio.adoTest.dataStructure.Tree;

import java.util.Iterator;

public abstract class TreeNode implements Comparable<TreeNode>, Iterable<TreeNode> { // TreeNodeIntf {
    // 左子节点
    TreeNode left;
    // 右子节点
    TreeNode right;
    // 当前节点值
    Comparable value;
    int level;
    // left - right
    int balance;
    int leftCount;
    int rightCount;

    public TreeNode(Comparable value) {
        this.value = value;
    }

    abstract TreeNode createNode(Comparable value);

    @Override
    public int compareTo(TreeNode o) {
        return this.value.compareTo(o);
    }

    public boolean hasLeftChild(){
        return null != left;
    }
    public boolean hasRightChild(){
        return null != right;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
