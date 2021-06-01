package com.example;

import java.util.Random;

public class RandomUtil {
    public static void setRandom(int[] arr) {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(10000000);
        }
    }

    public static void setNonRandom(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (i == (arr.length / 2) + 1) {
                continue;
            }
            arr[i] = i;
        }
    }
}