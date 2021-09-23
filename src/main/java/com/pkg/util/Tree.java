package com.pkg.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;

public final class Tree<T> implements Serializable {
    private LinkedList<Tree<T>> children = new LinkedList<>();
    private T value;
    private Tree<T> parent;

    /**
     * construct an empty tree
     * 
     * @since 2.0
     */

    public Tree() {
    }

    /**
     * @param value construct an empty tree with value
     * @since 2.0
     */

    public Tree(T value) {
        this.value = value;
    }

    /**
     * @param newval of this tree
     * @since 2.0
     */

    public synchronized void setValue(T newval) {
        this.value = newval;
    }

    /**
     * @return this value
     * @since 2.0
     */

    public synchronized T val() {
        return value;
    }

    /**
     * @return this tree's children
     * @since 2.0
     */

    public synchronized LinkedList<Tree<T>> children() {
        return children;
    }

    /**
     * @return this tree's parent
     * @since 2.0
     */

    public synchronized Tree<T> parent() {
        return parent;
    }

    /**
     * @param newNode to be add at the end of the child list
     * @since 2.0
     */

    @SafeVarargs
    public final synchronized void append(Tree<T>... newNode) {
        if (children.size() == 0) {
            for (Tree<T> j : newNode) {
                children.add(j);
                j.parent = this;
            }
            return;
        }
        insert(children.size() - 1, newNode);
    }

    /**
     * @param pos     to add
     * @param newNode to be added
     * @since 2.0
     */

    @SafeVarargs
    public final synchronized void insert(int pos, Tree<T>... newNode) {
        int t = pos;
        for (Tree<T> e : newNode) {
            children.add(t, e);
            e.parent = this;
            t++;
        }
    }

    /**
     * @param newNode to be added at the front of the child list
     * @since 2.0
     */

    @SafeVarargs
    public final synchronized void push(Tree<T>... newNode) {
        insert(0, newNode);
    }

    /**
     * @param index of child node to be delete
     * @since 2.0
     */

    public synchronized void delete(int index) {
        children.remove(index);
    }

    /**
     * @param delNode to be delete
     * @since 2.0
     */

    @SafeVarargs
    public final synchronized void delete(Tree<T>... delNode) {
        for (Tree<T> e : delNode) {
            children.remove(e);
        }
    }

    /**
     * @param index
     * @return element in child list by index
     * @since 2.0
     */

    public synchronized Tree<T> get(int index) {
        return children.get(index);
    }

    /**
     * @return this tree size
     * @since 2.0
     */

    public synchronized int size() {
        int size = 0;
        if (children.size() != 0) {
            for (int i = 0; i < children.size(); i++) {
                size += children.get(i).size();
            }
        }
        return size + 1;
    }

    /**
     * @param node
     * @return true if node is include in
     */

    @SafeVarargs
    public final synchronized boolean contains(Tree<T>... node) {
        for (int i = 0; i < children.size(); i++) {
            for (Tree<T> e : node) {
                if (e.equals(children.get(i)))
                    return true;
            }
        }
        return false;
    }

    /**
     * @return true if this tree is empty
     * @since 2.0
     */

    public synchronized boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @param node
     * @return index of node
     * @since 2.0
     */

    public synchronized int indexOf(Tree<T> node) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).equals(node))
                return i;
        }
        return -1;
    }

    /**
     * @param tr
     * @return true if this tree equal to tr
     * @since 2.0
     */

    public boolean equals(Tree<T> tr) {
        return tr.value.equals(this.value) && this.parent.equals(tr.parent) && this.children.equals(tr.children);
    }

    @Override
    public String toString() {
        String res = "";
        res += this.value;
        if (children.size() != 0) {
            for (Tree<T> child : children) {
                res += child.toString();
            }
        }
        return res;
    }

    /**
     * Serial Version
     * 
     * @since 2.0
     */

    @Serial
    private static final long serialVersionUID = 1L;
}
