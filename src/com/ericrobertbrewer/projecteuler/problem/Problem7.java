package com.ericrobertbrewer.projecteuler.problem;

import com.ericrobertbrewer.projecteuler.Utility;

import java.util.List;

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
		final int N = 10001;
		List<Long> primes = Utility.getNPrimes(N);
		return primes.get(N-1);
	}
}
