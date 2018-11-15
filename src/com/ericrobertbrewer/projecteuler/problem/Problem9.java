package com.ericrobertbrewer.projecteuler.problem;

import com.ericrobertbrewer.projecteuler.Utility;

public class Problem9 implements Problem {

    @Override
    public long getAnswer() {
        for (long c = 2; c < 1000L; c++) {
            final long cSquared = c * c;
            final long aEnd = c / 2L;
            for (long a = 1; a < aEnd; a++) {
                final long aSquared = a * a;
                final long bSquared = cSquared - aSquared;
                if (Utility.isSquare(bSquared)) {
                    final long b = (long) Math.sqrt(bSquared);
                    if (a + b + c == 1000L) {
                        return a * b * c;
                    }
                }
            }
        }
        return -1L;
    }
}
