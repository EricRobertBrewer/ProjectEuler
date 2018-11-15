package com.ericrobertbrewer.projecteuler.problem;

import com.ericrobertbrewer.projecteuler.Utility;

import java.util.Arrays;

public class Problem10 implements Problem {

    @Override
    public long getAnswer() {
        return Arrays.stream(Utility.getPrimesBelow(2000000)).sum();
    }
}
