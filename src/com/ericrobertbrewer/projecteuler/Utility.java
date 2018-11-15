package com.ericrobertbrewer.projecteuler;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public final class Utility {

    private static final String PRIMES_FILE_NAME = "primes.txt";
    private static final List<Long> PRIMES = new ArrayList<>();

    private static void loadPrimesIfNeeded() {
        if (PRIMES.size() > 0) {
            return;
        }
        final File file = new File(PRIMES_FILE_NAME);
        if (!file.exists()) {
            return;
        }
        if (!file.canRead() && !file.setReadable(true)) {
            System.err.println("Unable to read `" + file.getName() + "`.");
            return;
        }
        try {
            final Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final long prime = Long.parseLong(line);
                PRIMES.add(prime);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePrimes() {
        final File file = new File(PRIMES_FILE_NAME);
        if (file.exists() && !file.delete()) {
            System.err.println("Unable to delete existing file `" + file.getName() + "`.");
            return;
        }
        try {
            if (!file.createNewFile()) {
                System.err.println("Unable to create file `" + file.getName() + "`.");
                return;
            }
            if (!file.canWrite() && !file.setWritable(true)) {
                System.err.println("Unable to write to file `" + file.getName() + "`.");
                return;
            }
            final PrintStream out = new PrintStream(file);
            for (long prime : PRIMES) {
                out.println(prime);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPrime(long x, List<Long> primes) {
        final long root = (long) Math.sqrt(x);
        for (long prime : primes) {
            if (prime > root) {
                break;
            }
            if (x % prime == 0) {
                return false;
            }
        }
        return true;
    }
	
	/**
	 * Retrieve an array of prime numbers with an upper threshold {@code n}.
	 * @param n limit of primes
	 * @return array of prime numbers from 2...{@code n}
	 */
	public static long[] getPrimesBelow(final long n) {
		if (n < 2L) {
			return new long[] {};
		}
		loadPrimesIfNeeded();
		if (PRIMES.size() == 0) {
            PRIMES.add(2L);
            PRIMES.add(3L);
        }
        if (PRIMES.get(PRIMES.size() - 1) >= n) {
            final int nIndex = Collections.binarySearch(PRIMES, n);
            if (nIndex < 0) {
                return IntStream.range(0, -nIndex)
                        .mapToLong(PRIMES::get)
                        .toArray();
            } else {
                return IntStream.range(0, nIndex + 1)
                        .mapToLong(PRIMES::get)
                        .toArray();
            }
        }
        for (long p = PRIMES.get(PRIMES.size() - 1) + 2; p <= n; p += 2) {
            if (isPrime(p, PRIMES)) {
                PRIMES.add(p);
            }
        }
        savePrimes();
		return PRIMES.stream()
                .mapToLong(x -> x)
                .toArray();
	}
	
	/**
	 * Retrieve the first {@code n} prime numbers.
	 * @param n length of returned list
	 * @return array of first {@code n} prime numbers
	 */
	public static long[] getFirstPrimes(final int n) {
		if (n < 1) {
			return new long[] {};
		}
		if (n < 2) {
		    return new long[] { 2L };
        }
        loadPrimesIfNeeded();
        if (PRIMES.size() == 0) {
            PRIMES.add(2L);
            PRIMES.add(3L);
        }
        if (PRIMES.size() < n) {
            for (long p = PRIMES.get(PRIMES.size() - 1) + 2; PRIMES.size() < n; p++) {
                if (isPrime(p, PRIMES)) {
                    PRIMES.add(p);
                }
            }
            savePrimes();
        }
		return IntStream.range(0, n)
                .mapToLong(PRIMES::get)
                .toArray();
	}

	public static boolean isSquare(long x) {
	    final long root = (long) Math.sqrt(x);
	    return root * root == x;
    }

	private Utility() {
	}
}
