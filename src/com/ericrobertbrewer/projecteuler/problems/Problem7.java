package com.ericrobertbrewer.projecteuler.problems;

import java.util.List;

import com.ericrobertbrewer.projecteuler.SolutionInterface;
import com.ericrobertbrewer.projecteuler.Utility;

/**
 * 10001st prime
 * Problem 7
 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
 * What is the 10 001st prime number?
 * @author ebrewer
 */
public class Problem7 implements SolutionInterface {

	@Override
	public long getAnswer() {
		final int N = 10001;
		List<Long> primes = Utility.getNPrimes(N);
		return primes.get(N-1);
	}
}
