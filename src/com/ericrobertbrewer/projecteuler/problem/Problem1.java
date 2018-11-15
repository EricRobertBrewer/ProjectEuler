package com.ericrobertbrewer.projecteuler.problem;

public class Problem1 implements Problem {

	@Override
	public long getAnswer() {
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
		return sum;
	}
}
