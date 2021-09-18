package com.pkg.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public final class BinaryNode<T> implements Cloneable, Serializable {
    public int key;
    public T value;
    private HashMap<Integer, T> valueList = new HashMap<>();
    public BinaryNode<T> left;
    public BinaryNode<T> right;

    /**
     * create a key with no value to a binary tree with node
     * 
     * @param key
     * @since 1.5
     */

    public BinaryNode(int key) {
        this.key = key;
        value = null;
        left = right = null;
        valueList.put(key, value);
    }

    /**
     * @param key
     * @param value
     * @since 1.7
     */

    public BinaryNode(int key, T value) {
        this.key = key;
        this.value = value;
        left = right = null;
        valueList.put(key, value);
    }

    /**
     * @param root BST root
     * @return true if the binary tree is a BST
     * @since 1.5
     */

    public boolean isBST() {
        return isBSTStruct(this, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * @param node
     * @param min
     * @param max
     * @return true if the binary tree is a BST (structure of isBST)
     * @since 1.5
     */

    private boolean isBSTStruct(BinaryNode<T> node, int min, int max) {
        return node == null ? true
                : (node.key > max || node.key < min ? false
                        : isBSTStruct(node.left, min, node.key - 1) && isBSTStruct(node.right, node.key + 1, max));
    }

    /**
     * @param node target BST
     * @param key  search key
     * @return true if found key in node binary tree (search structure)
     * @since 1.6
     */

    private boolean searchStruct(BinaryNode<T> node, int key) {
        return node == null ? false
                : (key < node.key ? searchStruct(node.left, key)
                        : (key > node.key ? searchStruct(node.right, key) : true));
    }

    /**
     * @param key search key
     * @return true if found key in node binary tree
     * @since 1.6
     */

    public boolean search(int key) {
        return searchStruct(this, key);
    }

    /**
     * @param root BST
     * @param key  search key
     * @return root without node that its data equals key (delete() structure)
     * @since 1.6
     */

    private BinaryNode<T> deleteStruct(BinaryNode<T> root, int key) {
        if (root == null)
            return root;
        if (key < root.key)
            root.left = deleteStruct(root.left, key);
        else if (key > root.key)
            root.right = deleteStruct(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.key = minKey(root.right);
            root.right = deleteStruct(root.right, root.key);
        }
        return root;
    }

    /**
     * @param root
     * @return min value of root
     * @since 1.6
     */

    private int minKey(BinaryNode<T> root) {
        return root.left != null ? minKey(root.left) : root.key;
    }

    /**
     * @param key
     * @return root without node that its data equals key
     * @since 1.6
     */

    public BinaryNode<T> delete(int key) {
        return deleteStruct(this, key);
    }

    /**
     * @param root target BT
     * @param it   the iterator
     * @since 1.6
     */

    private void convertToBST(BinaryNode<T> root, Iterator<Integer> it) {
        if (root == null) {
            return;
        }
        convertToBST(root.left, it);
        root.key = it.next();
        convertToBST(root.right, it);
    }

    /**
     * @param root BT root
     * @param set  hold BT data
     * @since 1.6
     */

    private void extractKeys(BinaryNode<T> root, Set<Integer> set) {
        if (root == null) {
            return;
        }
        extractKeys(root.left, set);
        set.add(root.key);
        extractKeys(root.right, set);
    }

    /**
     * convert a BT to BST
     * 
     * @since 1.6
     */

    public void toBST() {
        Set<Integer> set = new TreeSet<Integer>();
        extractKeys(this, set);
        Iterator<Integer> it = set.iterator();
        convertToBST(this, it);
    }

    /**
     * @param root target BT
     * @since 1.6
     */

    private void printStruct(BinaryNode<T> root) {
        if (root == null) {
            return;
        }
        printStruct(root.left);
        System.out.print(root.value + " ");
        printStruct(root.right);
    }

    /**
     * print a BT
     * 
     * @since 1.6
     */

    public void print() {
        printStruct(this);
    }

    /**
     * flatten a bst
     * 
     * @param node target node (flatten() structure)
     * @since 1.7
     */

    private void flattenStruct(BinaryNode<T> node) {
        if (node == null || node.left == null && node.right == null)
            return;
        if (node.left != null) {
            flattenStruct(node.left);
            BinaryNode<T> tempNode = node.right;
            node.right = node.left;
            node.left = null;
            BinaryNode<T> curr = node.right;
            while (curr.right != null) {
                curr = curr.right;
            }
            curr.right = tempNode;
        }
        flattenStruct(node.right);
    }

    /**
     * flatten a bst
     * 
     * @since 1.7
     */

    public void flatten() {
        flattenStruct(this);
    }

    /**
     * result of the linked list
     */

    private LinkedList<Integer> res = new LinkedList<>();

    /**
     * @param root target node (toLinkedList() structure)
     * @param i    store linkedlist value
     * @return linkedlist form of the node
     * @since 1.7
     */

    private LinkedList<Integer> toLinkedList(BinaryNode<T> root) {
        res.clear();
        searchTree(root);
        return res;
    }

    /**
     * @param node search and return value in linkedList
     * @since 1.8
     */

    private void searchTree(BinaryNode<T> node) {
        if (node != null) {
            searchTree(node.left);
            res.add(node.key);
            searchTree(node.right);
        }
    }

    /**
     * @return linkedlist form of the current node
     * @since 1.7
     */

    public LinkedList<Integer> toLinkedList() {
        return toLinkedList(this);
    }

    /**
     * clone a node
     * 
     * @since 1.7
     */

    public BinaryNode<T> clone() {
        return this;
    }

    /**
     * Map method
     * 
     * @since 1.7
     */

    public int size() {
        return this.toLinkedList().size();
    }

    /**
     * Map method
     * 
     * @since 1.7
     */

    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Map method
     * 
     * @since 1.7
     */

    public boolean containsValue(T value) {
        return this.toLinkedList().indexOf(value) >= 0;
    }

    /**
     * Map method
     * 
     * @since 1.7
     */

    public void clear() {
        right = left = null;
        key = Integer.MIN_VALUE;
        value = null;
        valueList = new HashMap<Integer, T>();
    }

    /**
     * @return max value of a node root
     * @since 1.7
     */

    public Integer maxKey() {
        return maxKey(this);
    }

    /**
     * @param l
     * @return max value of a node root (BST)
     * @since 1.7
     */

    private Integer maxKey(BinaryNode<T> root) {
        return root.right != null ? maxKey(root.right) : root.key;
    }

    /**
     * @return min value of the linkedList
     * @since 1.7
     */

    public Integer minKey() {
        return minKey(this);
    }

    /**
     * @param index key
     * @return value by key
     * @since 1.8
     */

    public T get(int index) {
        return valueList.get(index);
    }

    /**
     * @return list of key in valueList
     * @since 1.8
     */

    public Set<Integer> keySet() {
        return valueList.keySet();
    }

    /**
     * Serial Version
     * 
     * @since 1.9
     */

    @Serial
    private static final long serialVersionUID = 1L;
}