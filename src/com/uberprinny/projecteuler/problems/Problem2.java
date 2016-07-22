package com.uberprinny.projecteuler.problems;

import java.util.ArrayList;

public class Problem2 {

	public static void printAnswer() {
		ArrayList<Integer> fib = new ArrayList<>();
		fib.add(0);
		fib.add(1);
		int sum = 0;
		int next = fib.get(fib.size()-2) + fib.get(fib.size()-1);
		while (next < 4000000) {
			if (next % 2 == 0) {
				sum += next;
			}
			fib.add(next);
			next = fib.get(fib.size()-2) + fib.get(fib.size()-1);
		}
		System.out.println(sum);
	}
}
