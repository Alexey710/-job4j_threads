package ru.job4j.concurrent.asynchronous.matrix;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Matrix {
    public static int[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        for (int k = 0; k < n; k++) {
            futures.put(k, getTask(matrix, k, k, 0, n - 1));
            futures.put(n + k, getTask(matrix, 0, n - 1, k, k));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Integer> getTask(
            int[][] data, int startRow, int endRow, int startCol, int endCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = startRow; i <= endRow; i++) {
                for (int j = startCol; j <= endCol; j++) {
                    sum += data[i][j];
                }
            }
            return sum;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] rsl = asyncSum(matrix);
        System.out.println(Arrays.toString(rsl));
    }
}
