package com.uberprinny.projecteuler;

import java.util.ArrayList;
import java.util.List;

public final class Utility {
	
	/**
	 * Retrieve a list of primes with an upper threshold {@code n}.
	 * @param n limit of primes
	 * @return list of prime numbers from 2...{@code n}
	 */
	public static List<Long> getPrimes(final long n) {
		ArrayList<Long> primes = new ArrayList<>();
		if (n < 2) {
			return primes;
		}
		primes.add(2L);
		if (n == 2) {
			return primes;
		}
		for (long i = 3; i <= n; i++) {
			boolean isPrime = true;
			for (int p = 0; p < primes.size() && primes.get(p) <= i/2 && isPrime; p++) {
				if (i % primes.get(p) == 0) {
					isPrime = false;
				}
			}
			if (isPrime) {
				primes.add(i);
			}
		}
		
		return primes;
	}
	
	/**
	 * Retrieve the first {@code n} prime numbers.
	 * @param n length of returned list
	 * @return list of first {@code n} prime numbers
	 */
	public static List<Long> getNPrimes(final int n) {
		ArrayList<Long> primes = new ArrayList<>();
		if (n < 1) {
			return primes;
		}
		primes.add(2L);
		if (n == 1) {
			return primes;
		}
		for (long i = 3; primes.size() < n; i++) {
			boolean isPrime = true;
			for (int p = 0; p < primes.size() && primes.get(p) <= i/2 && isPrime; p++) {
				if (i % primes.get(p) == 0) {
					isPrime = false;
				}
			}
			if (isPrime) {
				primes.add(i);
			}
		}
		return primes;
	}

	private Utility() {
	}
}
