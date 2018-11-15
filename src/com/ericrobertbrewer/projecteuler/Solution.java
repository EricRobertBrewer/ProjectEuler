package com.ericrobertbrewer.projecteuler;

import com.ericrobertbrewer.projecteuler.problem.*;

import java.util.HashMap;
import java.util.Map;

public class Solution {

    private interface ProblemProvider {
        Problem getProblem();
    }

    private static final Map<Integer, ProblemProvider> PROVIDERS = new HashMap<>();
    static {
        PROVIDERS.put(1, Problem1::new);
        PROVIDERS.put(2, Problem2::new);
        PROVIDERS.put(3, Problem3::new);
        PROVIDERS.put(4, Problem4::new);
        PROVIDERS.put(5, Problem5::new);
        PROVIDERS.put(6, Problem6::new);
        PROVIDERS.put(7, Problem7::new);
        PROVIDERS.put(8, Problem8::new);
        PROVIDERS.put(9, Problem9::new);
        PROVIDERS.put(10, Problem10::new);
    }

	public static void main(String[] args) {
		if (args.length < 1 || args.length > 1) {
			throw new IllegalArgumentException("Usage: <problem-number>");
		}
		final int number = Integer.parseInt(args[0]);
		final ProblemProvider provider = PROVIDERS.get(number);
		if (provider == null) {
		    throw new IllegalArgumentException("No problem registered for number: `" + number + "`.");
        }
        final Problem problem = provider.getProblem();
		System.out.println(problem.getAnswer());
	}
}
