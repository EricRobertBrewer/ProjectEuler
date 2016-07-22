package com.uberprinny.projecteuler;

public final class Problem1 {

	public static void printAnswer() {
		int sum = 0;
		/*
		for (int i = 3; i < 1000; i += 3) {
			sum += i % 5 == 0 ? 0 : 1;
		}
		for (int i = 5; i < 1000; i += 5) {
			sum += 1;
		}
		*/
		for (int i = 1; i < 1000; i++) {
			if (i % 3 == 0 || i % 5 == 0) {
				sum += i;
			}
		}
		System.out.println(sum);
	}
}
