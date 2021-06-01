package com.example;

public class RedBlackTree{
    class Node {
        int data; // holds the key
        Node parent; // pointer to the parent
        Node left; // pointer to left child
        Node right; // pointer to right child
        byte color; // 1 . Red, 0 . Black
    }
    private Node root;
    private Node TNULL;

    private int searchTreeHelper(Node node, int key) {
        if (node == TNULL || key == node.data) {
            return node.data;
        }
        if (key < node.data) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }

    private void fixDelete(Node x) {
        Node s;
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == 1) {
                    // case 3.1
                    s.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 0 && s.right.color == 0) {
                    // case 3.2
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.right.color == 0) {
                        // case 3.3
                        s.left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x.parent.right;
                    }

                    // case 3.4
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == 1) {
                    // case 3.1
                    s.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == 0) {
                    // case 3.2
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.left.color == 0) {
                        // case 3.3
                        s.right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    // case 3.4
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }

    private void rbTransplant(Node u, Node v){
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void deleteNodeHelper(Node node, int key) {
        Node z = TNULL;
        Node x, y;
        while (node != TNULL){
            if (node.data == key) {
                z = node;
            }

            if (node.data <= key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == TNULL) {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0){
            fixDelete(x);
        }
    }

    private void fixInsert(Node k){
        Node u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // uncle
                if (u.color == 1) {
                    // case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // case 3.2.2
                        k = k.parent;
                        rightRotate(k);
                    }
                    // case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; // uncle

                if (u.color == 1) {
                    // mirror case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        // mirror case 3.2.2
                        k = k.parent;
                        leftRotate(k);
                    }
                    // mirror case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    public RedBlackTree() {
        TNULL = new Node();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    public int search(int key) {
        return searchTreeHelper(this.root, key);
    }

    public Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void insert(int key) {
        // Ordinary Binary Search Insertion
        Node node = new Node();
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1; // new node must be red

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        // y is parent of x
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null){
            node.color = 0;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            //            System.out.println(node.key);
            inOrder(node.right);
        }
    }

    public Node getRoot(){
        return this.root;
    }

    public void delete(int key) {
        deleteNodeHelper(this.root, key);
    }

    public static void main(String[] args) {
        System.out.println(RedBlackTree.class.getName());
        for (int i = 10; i <= 100; i += 10) {
            RedBlackTree redBlackTree = new RedBlackTree();
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);

            long insertTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                redBlackTree.insert(arr[j]);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                redBlackTree.search(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            redBlackTree.inOrder(redBlackTree.getRoot());
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                redBlackTree.delete(arr[j]);
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }

        for (int i = 2000000; i <= 10000000; i += 1000000) {
            RedBlackTree redBlackTree = new RedBlackTree();
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);

            long insertTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                redBlackTree.insert(arr[j]);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                redBlackTree.search(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            redBlackTree.inOrder(redBlackTree.getRoot());
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns\n");

//            long deleteTime = System.nanoTime();
//            for (int j = 0; j < i; j++) {
//                redBlackTree.delete(arr[j]);
//            }
//            deleteTime = System.nanoTime() - deleteTime;
//            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }
    }
}