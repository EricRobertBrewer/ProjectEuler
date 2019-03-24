package com.ericrobertbrewer.projecteuler;

class NumberNames {

    private static final String[] FIRST_TWENTY = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };
    private static final String[] MULTIPLES_OF_TEN = {
            "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };
    private static final String HUNDRED = "hundred";
    private static final String THOUSAND = "thousand";

    static String getName(int x) {
        return getName(x, true);
    }

    static String getName(int x, boolean spaces) {
        if (x < 0) {
            return "negative" + (spaces ? " " : "") + getName(-x, spaces);
        }
        if (x < FIRST_TWENTY.length) {
            return FIRST_TWENTY[x];
        }
        if (x < 100) {
            final int tens = x / 10;
            final int ones = x % 10;
            return MULTIPLES_OF_TEN[tens] + (spaces ? " " : "") + (ones != 0 ? getName(ones, spaces) : "");
        }
        if (x < 1000) {
            final int hundreds = x / 100;
            final int remainder = x % 100;
            return FIRST_TWENTY[hundreds] + (spaces ? " " : "") + HUNDRED +
                    (remainder != 0 ? ((spaces ? " " : "") + "and" + (spaces ? " " : "") + getName(remainder, spaces)) : "");
        }
        if (x == 1000) {
            return FIRST_TWENTY[1] + (spaces ? " " : "") + THOUSAND;
        }
        throw new IllegalArgumentException("Can't be above one thousand.");
    }
}
