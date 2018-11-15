package com.ericrobertbrewer.projecteuler.problem;

import com.ericrobertbrewer.projecteuler.Utility;

/**
 * 10001st prime
 * Problem 7
 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
 * What is the 10 001st prime number?
 * @author ebrewer
 */
public class Problem7 implements Problem {

	@Override
	public long getAnswer() {
		final int n = 10001;
		final long[] primes = Utility.getFirstPrimes(n);
		return primes[n - 1];
	}
}
