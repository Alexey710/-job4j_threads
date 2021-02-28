package ru.job4j.concurrent.asynchronous.matrix;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sumsObj = new Sums[n];
        for (int k = 0; k < n; k++) {
            int rowSum = countSum(matrix, k, k, 0, n - 1);
            int colSum = countSum(matrix, 0, n - 1, k, k);
            sumsObj[k] = new Sums(rowSum, colSum);
        }
        return sumsObj;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sumsObj = new Sums[n];
        for (int k = 0; k < n; k++) {
            int rowSum = getTask(matrix, k, k, 0, n - 1).get();
            int colSum = getTask(matrix, 0, n - 1, k, k).get();
            sumsObj[k] = new Sums(rowSum, colSum);
        }
        return sumsObj;
    }

    public static CompletableFuture<Integer> getTask(
            int[][] data, int startRow, int endRow, int startCol, int endCol) {
        return CompletableFuture.supplyAsync(
                () -> countSum(data, startRow, endRow, startCol, endCol));
    }

    private static int countSum(
            int[][] data, int startRow, int endRow, int startCol, int endCol) {
        int sum = 0;
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                sum += data[i][j];
            }
        }
        return sum;
    }

    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

    }

}
