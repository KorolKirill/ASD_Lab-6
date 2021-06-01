package com.example;

public class BinaryTree {
    Node root;

    public void add(int value) {
        root = addRecursive(root, value);
    }

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        }

        return current;
    }

    public int getSize() {
        return getSizeRecursive(root);
    }

    private int getSizeRecursive(Node current) {
        return current == null ? 0 : getSizeRecursive(current.left) + 1 + getSizeRecursive(current.right);
    }

    public boolean find(int value) {
        return findNodeRecursive(root, value);
    }

    private boolean findNodeRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }

        if (value == current.value) {
            return true;
        }

        return value < current.value
                ? findNodeRecursive(current.left, value)
                : findNodeRecursive(current.right, value);
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value == current.value) {
            // Case 1: no children
            if (current.left == null && current.right == null) {
                return null;
            }

            // Case 2: only 1 child
            if (current.right == null) {
                return current.left;
            }

            if (current.left == null) {
                return current.right;
            }

            // Case 3: 2 children
            int smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;
        }
        if (value < current.value) {
            current.left = deleteRecursive(current.left, value);
            return current;
        }

        current.right = deleteRecursive(current.right, value);
        return current;
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
//            System.out.println(node.value);
            inOrder(node.right);
        }
    }

    class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }

    public static void main(String[] args) {
        System.out.println(BinaryTree.class.getName());
        for (int i = 100000; i <= 1000000; i += 100000) {
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);
            BinaryTree binaryTree = new BinaryTree();

            long insertTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                binaryTree.add(arr[j]);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                binaryTree.find(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            binaryTree.inOrder(binaryTree.root);
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                binaryTree.delete(arr[j]);
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }

        for (int i = 2000000; i <= 10000000; i += 1000000) {
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);
            BinaryTree binaryTree = new BinaryTree();

            long insertTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                binaryTree.add(arr[j]);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                binaryTree.find(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            binaryTree.inOrder(binaryTree.root);
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                binaryTree.delete(arr[j]);
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }
    }
}