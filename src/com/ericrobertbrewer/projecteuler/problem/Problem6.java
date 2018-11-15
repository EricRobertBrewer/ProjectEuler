package com.ericrobertbrewer.projecteuler.problem;

import java.util.stream.LongStream;

public class Problem6 implements Problem {

    @Override
    public long getAnswer() {
        final int n = 100;
        final long sumOfSquares = getSumOfSquares(n);
        final long squareOfSum = getSquareOfSum(n);
        return squareOfSum - sumOfSquares;
    }

    private static long getSumOfSquares(int n) {
        return LongStream.range(1, n + 1)
                .map(x -> x * x)
                .sum();
    }

    private static long getSquareOfSum(int n) {
        final long sum = LongStream.range(1, n + 1).sum();
        return sum * sum;
    }
}
