package com.example;

import java.util.Arrays;

public class LinkedList {
    public class Node {
        private int data;
        private Node next;
        private Node previous;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }

    private Node head;
    private Node tail;
    private int length;

    public LinkedList() {
        head = tail = null;
        length = 0;
    }

    public Node getHead() {
        return head;
    }

    private void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    private void setTail(Node tail) {
        this.tail = tail;
    }

    public int getLength() {
        return length;
    }

    private void setLength(int length) {
        this.length = length;
    }

    public void addFirst(int data) {
        Node node = new Node();
        node.setData(data);
        node.setNext(this.getHead());

        if (this.getHead() != null) {
            this.getHead().setPrevious(node);
        }

        if (this.getLength() == 0) {
            this.setTail(node);
        }
        this.setHead(node);

        this.length++;
    }

    public void addLast(int data) {
        Node node = new Node();
        node.setData(data);
        node.setPrevious(this.getTail());

        if (this.getTail() != null) {
            this.getTail().setNext(node);
        }

        if (length == 0) {
            this.setHead(node);
        }
        this.setTail(node);

        this.length++;
    }

    public void insert(int data, int index) {
        if (index < 1 || index > this.getLength() + 1) {
            return;
        }
        if (index == 1) {
            this.addFirst(data);
            return;
        }
        if (index == this.getLength() + 1) {
            this.addLast(data);
            return;
        }
        Node node = new Node();
        node.setData(data);
        Node prevNode = new Node();
        Node prevNodeAtIndex = new Node();
        int middle = this.getLength() / 2;
        if (index <= middle) {
            Node loopNode = this.getHead();
            for (int i = 1; i < index; i++) {
                if (i == index - 1) {
                    prevNode = loopNode;
                }
                loopNode = loopNode.getNext();
            }
            prevNodeAtIndex = prevNode.getNext();
        } else {
            Node loopNode = this.getTail();
            for (int i = this.getLength(); i > index - 1; i--) {
                if (i == index) {
                    prevNodeAtIndex = loopNode;
                }
                loopNode = loopNode.getPrevious();
            }
            prevNode = prevNodeAtIndex.getPrevious();
        }
        prevNode.setNext(node);
        node.setPrevious(prevNode);
        node.setNext(prevNodeAtIndex);
        prevNodeAtIndex.setPrevious(node);

        this.length++;
    }

    public int[] getSorted() {
        int[] arr = new int[length];
        int i = -1;
        for (Node node = this.getHead(); node.getNext() != null; node = node.getNext()) {
            arr[++i] = node.getData();
        }
        arr[++i] = this.getTail().getData();
        Arrays.sort(arr);
        return arr;
    }

    public int deleteElement(int index) {
        if (index < 1 || index > this.getLength()) {
            return -1;
        }

        if (index == 1) {
            return this.deleteFirst();
        } else if (index == this.getLength()) {
            return this.deleteLast();
        }

        int middle = this.getLength() / 2;
        int counter;
        Node deletedNode;
        if (index <= middle) {
            deletedNode = this.getHead();
            counter = 1;
            while (counter < index) {
                deletedNode = deletedNode.getNext();
                counter++;
            }
        } else {
            deletedNode = this.getTail();
            counter = this.getLength();
            while (counter > index) {
                deletedNode = deletedNode.getPrevious();
                counter--;
            }
        }
        Node previousNode = deletedNode.getPrevious();
        Node nextNode = deletedNode.getNext();

        if (previousNode != null) {
            previousNode.setNext(nextNode);
        }
        if (nextNode != null) {
            nextNode.setPrevious(previousNode);
        }

        this.length--;
        return deletedNode.getData();
    }

    public int deleteFirst() {
        if (this.getLength() != 0) {
            Node node = this.getHead();
            int value = node.getData();
            if (this.getLength() == 1) {
                this.setTail(null);
            }
            this.setHead(node.getNext());
            this.length--;
            return value;
        } else return -1;
    }

    public int deleteLast() {
        if (this.getLength() != 0) {
            Node node = this.getTail();
            int value = node.getData();
            if (this.getLength() == 1) {
                this.setHead(null);
            }
            this.setTail(node.getPrevious());
            this.length--;
            return value;
        } else return -1;
    }

    public void replaceFirst(int newValue) {
        this.getHead().setData(newValue);
    }

    public void replaceLast(int newValue) {
        this.getTail().setData(newValue);
    }

    public void replace(int newValue, int index) {
        Node replaceNode;
        int middle = this.getLength() / 2;
        if (index <= middle) {
            replaceNode = this.getHead();
            for (int i = 1; i < index + 1; i++) {
                if (i == index) {
                    replaceNode.setData(newValue);
                    break;
                }
                replaceNode = replaceNode.getNext();
            }
        } else {
            replaceNode = this.getTail();
            for (int i = this.getLength(); i > index - 1; i--) {
                if (i == index) {
                    replaceNode.setData(newValue);
                    break;
                }
                replaceNode = replaceNode.getPrevious();
            }
        }
    }

    public int indexAt(int value) {
        Node valueNode = this.getHead();
        int index = 0;
        while(valueNode != null){
            if (valueNode.data == value) {
                return index;
            }
            valueNode = valueNode.next;
            index++;
        }
        return -1;
    }

    public int sum() {
        int sum = 0;
        for (Node node = this.getHead(); node.getNext() != null; node = node.getNext()) {
            sum += node.getData();
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(LinkedList.class.getName());
        for (int i = 10000; i <= 100000; i += 10000) {
            LinkedList linkedList = new LinkedList();
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);

            long insertTime = System.nanoTime();
            for (int j = 1; j <= i; j++) {
                linkedList.insert(arr[j - 1], j);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                linkedList.indexAt(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            int[] sorted = linkedList.getSorted();
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                linkedList.deleteFirst();
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }

        for (int i = 200000; i <= 500000; i += 100000) {
            LinkedList linkedList = new LinkedList();
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);

            long insertTime = System.nanoTime();
            for (int j = 1; j <= i; j++) {
                linkedList.insert(arr[j - 1], j);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                linkedList.indexAt(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            int[] sorted = linkedList.getSorted();
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                linkedList.deleteFirst();
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }
    }
}