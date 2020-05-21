package com.my.studio.adoTest.algorithm.sort;

public class Test {
    private static int[] arr = {9, 4, 8, 7, 2, 6, 1, 10, 3, 5};

    public static void main(String args[]) {
        quickSort(arr, 0, arr.length - 1);
        /*arr = quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + ",");
        }*/
    }

    static void quickSort(int[] arr, int start, int end) {
        int middle = getMid(start, end);
        sortLeft(arr, start, middle);
        sortRight(arr, middle + 1, end);
    }

    static int getMid(int start, int end) {
        return (start + end) / 2;
    }

    static void sortLeft(int[] arr, int start, int end) {
        int temp = arr[start];
        if (temp >= arr[end]) {

        }
        // quickSort(arr, start, end);
    }

    static void sortRight(int[] arr, int start, int end) {

        // quickSort(arr, start, end);
    }
}
