package com.ericrobertbrewer.projecteuler;

import java.util.*;
import java.util.stream.LongStream;

final class Utility {

	static boolean isSquare(final long x) {
	    final long root = (long) Math.sqrt(x);
	    return root * root == x;
    }

    static boolean isPalindrome(final int x) {
        final String s = String.valueOf(x);
        for (int i = 0; i < s.length()/2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    static long getTriangleNumber(final int i) {
	    return LongStream.range(1L, (long) i + 1).sum();
    }

    static int getFactorCount(final long x) {
	    if (x < 0) {
	        throw new IllegalArgumentException("Can't be negative.");
        }
	    if (x == 0) {
	        return 0;
        }
	    if (Prime.isPrime(x)) {
	    	return 2;
		}
	    final Map<Long, Integer> primeMultiplicities = Prime.getPrimeMultiplicities(x);
		int count = 1;
		for (long prime : primeMultiplicities.keySet()) {
			count *= primeMultiplicities.get(prime) + 1;
		}
		return count;
    }

	private Utility() {
	}
}
