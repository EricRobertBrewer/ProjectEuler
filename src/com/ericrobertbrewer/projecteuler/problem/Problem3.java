package com.ericrobertbrewer.projecteuler.problem;

import java.util.ArrayList;
import java.util.List;

public class Problem3 implements Problem {

	private static List<Long> primes = new ArrayList<>();

	@Override
	public long getAnswer() {
		long n = 600851475143L;
		primes.add(2L);
//		primes.add(3L);
//		primes.add(5L);
//		primes.add(7L);
		ArrayList<Long> factors = new ArrayList<>();
		for (long i = 3; i <= n; i += 2) {
			boolean isPrime = true;
			for (int j = 1; j < primes.size() && isPrime; j++) {
				if (i % primes.get(j) == 0) {
					isPrime = false; // not prime
				}
			}
			if (isPrime) {
				primes.add(i);
				while (n % i == 0) {
					factors.add(i);
					n /= i;
				}
			}
		}
		return factors.get(factors.size()-1);
//		for (long i = n / 2; i > 0; i-=2) {
//			if (n % i == 0 && isPrime(i)) {
//				System.out.println(i);
//				break;
//			}
//		}
	}
	
	public static boolean isPrime(long n) {
		for (long i = n / 2; i > 1; i-=2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}