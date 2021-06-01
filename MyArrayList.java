package com.example;

import java.util.Arrays;

public class MyArrayList {
    private int capacity;
    private final float loadFactor = 0.75F;
    private int[] array;
    private int size = 0;

    public int getCapacity() {
        return capacity;
    }

    private void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getLoadFactor() {
        return loadFactor;
    }

    public MyArrayList() {
        this.capacity = 16;
        this.array = new int[this.capacity];
    }

    public MyArrayList(int capacity) {
        if (capacity > 1) {
            this.capacity = capacity;
        }
        this.array = new int[this.capacity];
    }

    private void checkSpace() {
        if (this.getCapacity() * this.getLoadFactor() <= this.size) {
            this.extendCapacity();
        }
    }

    private void extendCapacity() {
        int[] prevArray = this.array;
        int newCapacity = this.getCapacity() * 2;
        this.array = new int[newCapacity];
        System.arraycopy(prevArray, 0, this.array, 0, this.size);
        this.setCapacity(newCapacity);
    }

    public void addFirst(int value) {
        this.insert(value, 1);
    }

    public void insert(int value, int index) {
        if (index < 1 || index > this.size + 1) {
            return;
        }

        if (index == this.size + 1) {
            this.addLast(value);
            return;
        }
        int newIndex = index - 1;
        for (int i = this.size; i > newIndex; i--) {
            this.array[i] = this.array[i - 1];
        }
        this.array[newIndex] = value;
        this.size++;

        this.checkSpace();
    }

    public int[] getSorted() {
        int[] newArr = new int[this.size];
        System.arraycopy(array, 0, newArr, 0, this.size);
        Arrays.sort(newArr);
        return newArr;
    }

    public void addLast(int value) {
        this.array[size] = value;
        this.size++;
        this.checkSpace();
    }

    public int deleteFirst() {
        return this.deleteElement(1);
    }

    public int deleteElement(int index) {
        if (index < 1 || index > this.size) {
            return -1;
        }

        if (index == this.size) {
            return this.deleteLast();
        }
        int newIndex = index - 1;
        int value = this.array[newIndex];
        for (int i = newIndex; i < this.size - 1; i++) {
            this.array[i] = this.array[i + 1];
        }
        this.array[this.size - 1] = 0;
        this.size--;
        return value;
    }


    public int deleteLast() {
        int value = this.array[this.size - 1];
        this.array[this.size - 1] = 0;
        this.size--;
        return value;
    }


    public void replaceFirst(int newValue) {
        this.replace(newValue, 1);
    }


    public void replaceLast(int newValue) {
        this.replace(newValue, this.size);
    }


    public void replace(int newValue, int index) {
        if (index > this.size || index < 1) {
            return;
        }
        index--;
        this.array[index] = newValue;
    }


    public int sum() {
        int sum = 0;
        for (int i = 0; i < this.size; i++) {
            sum += this.array[i];
        }
        return sum;
    }


    public int indexAt(int value) {
        int index = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] == value) {
                index = i;
                break;
            }
        }
        return index + 1;
    }


    public void show() {
        for (int i = 0; i < this.size; i++) {
            System.out.print(this.array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(MyArrayList.class.getName());
        for (int i = 10000; i <= 100000; i += 10000) {
            MyArrayList arrayList = new MyArrayList();
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);

            long insertTime = System.nanoTime();
            for (int j = 1; j <= i; j++) {
                arrayList.insert(arr[j - 1], j);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                arrayList.indexAt(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            int[] sorted = arrayList.getSorted();
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                arrayList.deleteFirst();
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }

        for (int i = 200000; i <= 500000; i += 100000) {
            MyArrayList arrayList = new MyArrayList();
            int[] arr = new int[i];
            RandomUtil.setRandom(arr);

            long insertTime = System.nanoTime();
            for (int j = 1; j <= i; j++) {
                arrayList.insert(arr[j - 1], j);
            }
            insertTime = System.nanoTime() - insertTime;
            System.out.println("Insert of " + i + " elements: " + insertTime / i + " ns");

            long searchTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                arrayList.indexAt(arr[j]);
            }
            searchTime = System.nanoTime() - searchTime;
            System.out.println("Search of " + i + " elements: " + searchTime / i + " ns");

            long sortingTime = System.nanoTime();
            int[] sorted = arrayList.getSorted();
            sortingTime = System.nanoTime() - sortingTime;
            System.out.println("Sorting of " + i + " elements: " + sortingTime / i + " ns");

            long deleteTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                arrayList.deleteFirst();
            }
            deleteTime = System.nanoTime() - deleteTime;
            System.out.println("Delete of " + i + " elements: " + deleteTime / i + " ns\n");
        }
    }
}