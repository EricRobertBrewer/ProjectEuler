package com.uberprinny.projecteuler;

import java.util.ArrayList;
import java.util.List;

public final class Utility {
	
	/**
	 * Retrieve a list of primes
	 * @param n limit of primes
	 * @return list of prime numbers from 2...n
	 */
	public static final List<Long> getPrimes(long n) {
		ArrayList<Long> primes = new ArrayList<>();
		if (n > 1) {
			primes.add(2L);
			if (n > 2) {
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
			}
		}
		
		return primes;
	}

	private Utility() {
	}
}
