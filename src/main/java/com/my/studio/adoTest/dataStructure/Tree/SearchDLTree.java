package com.my.studio.adoTest.dataStructure.Tree;

import java.util.Comparator;
import java.util.Objects;

public class SearchDLTree implements TreeIntf {

    NodeDL root;
    // 比较器
    Comparator<NodeDL> comparator;
    // 是否包含同值元素
    boolean isUnique = false;

    public SearchDLTree(Comparable value, Comparator<NodeDL> comparator) {
        Objects.requireNonNull(value);
        this.root = new NodeDL(value);
        this.comparator = comparator;
    }

    public SearchDLTree(Comparable value, Comparator<NodeDL> comparator, boolean isUnique) {
        Objects.requireNonNull(value);
        this.root = new NodeDL(value);
        this.comparator = comparator;
        this.isUnique = isUnique;
    }

    @Override
    public void add(Comparable value) {
        Objects.requireNonNull(value);
        add(root, value);
    }

    private void add(NodeDL node, Comparable value) {
        int cp = value.compareTo(node.value);
        if (cp == 0 && isUnique) return;
        if (cp < 0) {
            add((NodeDL)node.left, value, !node.hasLeftChild());
            node.leftCount++;
        } else {
            add((NodeDL)node.right, value, !node.hasRightChild());
            node.rightCount++;
        }
        int leftSubLevel = node.hasLeftChild() ? node.left.level : 0;
        int rightSubLevel = node.hasRightChild() ? node.right.level : 0;
        node.level = Math.max(leftSubLevel, rightSubLevel) + 1;
    }

    private void add(NodeDL node, Comparable value, boolean needCreate) {
        if(needCreate) {
            node = node.parent.createNode(value);
        } else {
            add(node, value);
        }
    }

    private void rebalance(NodeDL node) {

    }

    @Override
    public void remove() {

    }

    @Override
    public void iterator() {

    }
}
