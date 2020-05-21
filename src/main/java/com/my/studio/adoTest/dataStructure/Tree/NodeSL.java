package com.my.studio.adoTest.dataStructure.Tree;

/**
 * 单向链表
 */
public class NodeSL extends TreeNode {

    public NodeSL(Comparable value) {
        super(value);
    }

    public NodeSL createNode(Comparable value) {
        return new NodeSL(value);
    }

}
