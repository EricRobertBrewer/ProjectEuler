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
	    if (x < 0L) {
	        throw new IllegalArgumentException("Can't be negative.");
        }
	    if (x == 0L) {
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

    static Set<Long> getFactors(final long x) {
		final Set<Long> factors = new HashSet<>();
		final Map<Long, Integer> primeMultiplicities = Prime.getPrimeMultiplicities(x);
		final long[] primes = new long[primeMultiplicities.keySet().size()];
		final int[] multiplicities = new int[primes.length];
		int index = 0;
		for (long prime : primeMultiplicities.keySet()) {
			primes[index] = prime;
			multiplicities[index] = primeMultiplicities.get(prime);
			index++;
		}
		final List<int[]> permutations = getPermutations(multiplicities);
		for (int[] permutation : permutations) {
			long product = 1L;
			for (int i = 0; i < permutation.length; i++) {
				product *= pow(primes[i], permutation[i]);
			}
			factors.add(product);
		}
		return factors;
	}

	private static List<int[]> getPermutations(final int[] counts) {
		return getPermutations(counts, 0);
	}

	private static List<int[]> getPermutations(final int[] counts, int index) {
		final List<int[]> permutations = new LinkedList<>();
		// The base case is reached when there are no tail permutations.
		if (index == counts.length - 1) {
			for (int i = 0; i <= counts[index]; i++) {
				final int[] permutation = new int[counts.length];
				permutation[index] = i;
				permutations.add(permutation);
			}
			return permutations;
		}
		// Augment tail permutations.
		final List<int[]> tailPermutations = getPermutations(counts, index + 1);
		for (int i = 0; i <= counts[index]; i++) {
			for (int[] tailPermutation : tailPermutations) {
				final int[] permutation = new int[counts.length];
				permutation[index] = i;
				System.arraycopy(tailPermutation, index + 1, permutation, index + 1, counts.length - index - 1);
				permutations.add(permutation);
			}
		}
		return permutations;
	}

	static long pow(final long x, final int exp) {
		if (exp < 0L) {
			throw new IllegalArgumentException("Can't be negative.");
		}
		long product = 1L;
		for (int i = 0; i < exp; i++) {
			product *= x;
		}
		return product;
	}

	static long getPowerBase(final long x) {
		final Map<Long, Integer> primeMultiplicities = Prime.getPrimeMultiplicities(x);
		if (areAllIdentical(new ArrayList<>(primeMultiplicities.values()))) {
			return primeMultiplicities.keySet().stream().reduce(1L, (p, c) -> p * c);
		}
		return x;
	}

	private static boolean areAllIdentical(List<Integer> a) {
		for (int i = 1; i < a.size(); i++) {
			if (!a.get(i).equals(a.get(0))) {
				return false;
			}
		}
		return true;
	}

	private Utility() {
	}
}
