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
		final List<int[]> permutations = getCountPermutations(multiplicities, 0);
		for (int[] permutation : permutations) {
			long product = 1L;
			for (int i = 0; i < permutation.length; i++) {
				product *= pow(primes[i], permutation[i]);
			}
			factors.add(product);
		}
		return factors;
	}

	private static List<int[]> getCountPermutations(final int[] counts, int index) {
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
		final List<int[]> tailPermutations = getCountPermutations(counts, index + 1);
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

	static long getSumPermutationCount(long[] coins, long y) {
		long sum = 0L;
		for (int index = 0; index < coins.length; index++) {
			sum += getSumPermutationCount(coins, y, index, false);
		}
		return sum;
	}

	private static long getSumPermutationCount(long[] coins, long y, int index, boolean isSub) {
		if (y == 0L) {
			return 1L;
		}
		final long coin = coins[index];
		if (index == 0) {
			if (y % coin == 0) {
				return 1L;
			}
			return 0L;
		}
		long sum = 0L;
		for (long n = isSub ? 0L : 1L; n * coin <= y; n++) {
			sum += getSumPermutationCount(coins, y - n * coin, index - 1, true);
		}
		return sum;
	}

	static List<int[]> getIndexPermutations(final int n) {
		final List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			indices.add(i);
		}
		return getPermutations(indices);
	}

	private static List<int[]> getPermutations(List<Integer> a) {
		final List<int[]> permutations = new ArrayList<>();
		if (a.size() == 1) {
			final int[] permutation = new int[1];
			permutation[0] = a.get(0);
			permutations.add(permutation);
		} else {
			for (int i = 0; i < a.size(); i++) {
				final List<Integer> tail = new ArrayList<>();
				for (int j = 0; j < a.size(); j++) {
					if (i != j) {
						tail.add(a.get(j));
					}
				}
				final List<int[]> tailPermutations = getPermutations(tail);
				for (int[] tailPermutation : tailPermutations) {
					final int[] permutation = new int[a.size()];
					permutation[0] = a.get(i);
					System.arraycopy(tailPermutation, 0, permutation, 1, tailPermutation.length);
					permutations.add(permutation);
				}
			}
		}
		return permutations;
	}

	static long gcm(long n, long d) {
		while (d % n != 0L) {
			long t = n;
			n = d % n;
			d = t;
		}
		return n;
	}

	static long factorial(final long x) {
		if (x < 0L) {
			throw new IllegalArgumentException("Can't be negative.");
		}
		if (x < 2L) {
			return 1L;
		}
		long result = 1L;
		for (long i = 2L; i <= x; i++) {
			result *= i;
		}
		return result;
	}

	private static int getDigitCount(long x, final long radix) {
		int count = 0;
		while (x > 0) {
			x /= radix;
			count++;
		}
		return count;
	}

	static long[] getDigits(long x) {
		return getDigits(x, 10L);
	}

	static long[] getDigits(long x, final long radix) {
		final long[] digits = new long[getDigitCount(x, radix)];
		int i = 0;
		while (x > 0) {
			digits[digits.length - i - 1] = x % radix;
			x /= radix;
			i++;
		}
		return digits;
	}

	static boolean isPalindrome(long[] a) {
		for (int i = 0; i < a.length / 2; i++) {
			if (a[i] != a[a.length - i - 1]) {
				return false;
			}
		}
		return true;
	}

	private Utility() {
	}
}
