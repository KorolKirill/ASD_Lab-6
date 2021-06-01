package com.example;

public class AVLTree {

    public class Node {
        int key;
        int height;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;

    public Node search(int key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) {
                break;
            }
            current = current.key < key ? current.right : current.left;
        }
        return current;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    public Node getRoot() {
        return root;
    }

    public int height() {
        return root == null ? -1 : root.height;
    }

    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        } else if (node.key > key) {
            node.left = insert(node.left, key);
        } else if (node.key < key) {
            node.right = insert(node.right, key);
        } else {
            node.key = key;
        }
        return rebalance(node);
    }

    private Node delete(Node node, int key) {
        if (node == null) {
            return node;
        } else if (node.key > key) {
            node.left = delete(node.left, key);
        } else if (node.key < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = delete(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private Node mostLeftChild(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right)) {
                z = rotateRight(z);
            } else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(Node n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
//            System.out.println(node.key);
            inOrder(node.right);
        }
    }

    public static void main(String[] args) {
        System.out.println(AVLTree.class.getName());
        for (int i = 100000; i <= 1000000; i += 100000) {
            AVLTree avlTree = new AVLTree();
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);

            long insertTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                avlTree.insert(arr[j]);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                avlTree.search(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            avlTree.inOrder(avlTree.getRoot());
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                avlTree.delete(arr[j]);
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }

        for (int i = 2000000; i <= 10000000; i += 1000000) {
            AVLTree avlTree = new AVLTree();
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);

            long insertTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                avlTree.insert(arr[j]);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                avlTree.search(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            avlTree.inOrder(avlTree.getRoot());
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                avlTree.delete(arr[j]);
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }
    }
}