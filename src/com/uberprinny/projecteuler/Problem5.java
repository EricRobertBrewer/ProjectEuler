package com.uberprinny.projecteuler;

import java.util.HashMap;
import java.util.List;

/**
 * Smallest multiple
 * Problem 5
 * 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
 * What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
 * @author ebrewer
 */
public class Problem5 implements SolutionInterface {

	public static final long N = 20;
	
	@Override
	public long getAnswer() {
		// initialize list of primes up to N, and map of prime factors from 2 -> N
		List<Long> primes = Utility.getPrimes(N);
		HashMap<Long, Integer> factors = new HashMap<>();
		for (long prime : primes) {
			factors.put(prime, 0);
		}
		
		// count prime factors of each number from 2 -> N
		for (long i = N; i >= N/2; i--) {
			long product = i; // product of some primes; continually divide it (if divisible) by primes found in list of primes
			for (long prime : primes) {
				int count = 0; // order of magnitude of this prime as a factor of product
				while (product > 1 && product % prime == 0) { // if divisible...
					count++;
					if (count > factors.get(prime)) {
						factors.put(prime, count); // also increment factor count if it exceeds previous count
					}
					product /= prime;
				}
			}
		}
		
		long result = 1;
		for (long prime : primes) {
			for (int i = 0; i < factors.get(prime); i++) {
				result *= prime;
			}
		}
		return result;
	}
}
