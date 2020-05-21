package com.my.studio.adoTest.dataStructure.Tree;

/**
 * 双向链表
 */
public class NodeDL extends TreeNode {
    // 父节点
    NodeDL parent;

    NodeDL createNode(Comparable value) {
        return (new NodeDL(value, this));
    }

    public NodeDL(Comparable value) { super(value); }

    public NodeDL(Comparable value, NodeDL parent) {
        super(value);
        this.parent = parent;
    }
}
