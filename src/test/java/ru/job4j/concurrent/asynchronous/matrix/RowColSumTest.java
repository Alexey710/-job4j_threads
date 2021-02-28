package ru.job4j.concurrent.asynchronous.matrix;

import org.junit.Assert;
import org.junit.Test;
import ru.job4j.concurrent.asynchronous.matrix.RowColSum.Sums;
import java.util.concurrent.ExecutionException;

public class RowColSumTest {

    @Test
    public void sum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] rsl = RowColSum.sum(matrix);
        Assert.assertEquals(6, rsl[0].getRowSum());
        Assert.assertEquals(12, rsl[0].getColSum());
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] rsl = RowColSum.asyncSum(matrix);
        Assert.assertEquals(6, rsl[0].getRowSum());
        Assert.assertEquals(12, rsl[0].getColSum());
    }
}