package com.ericrobertbrewer.projecteuler;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Prime {

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
        if (PRIMES.size() == 0) {
            PRIMES.add(2L);
            PRIMES.add(3L);
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

    static boolean isPrime(final long x) {
        loadPrimesIfNeeded();
        final long root = (long) Math.sqrt(x);
        for (long prime : PRIMES) {
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
     * Retrieve a list of prime numbers with an upper threshold {@code x}.
     * @param x limit of primes
     * @return list of prime numbers from 2...{@code x}
     */
    static List<Long> getPrimesBelow(final long x) {
        if (x < 2L) {
            return new ArrayList<>();
        }
        loadPrimesIfNeeded();
        if (PRIMES.get(PRIMES.size() - 1) >= x) {
            final int nIndex = Collections.binarySearch(PRIMES, x);
            if (nIndex < 0) {
                return IntStream.range(0, -nIndex)
                        .mapToLong(PRIMES::get)
                        .boxed()
                        .collect(Collectors.toList());
            } else {
                return IntStream.range(0, nIndex + 1)
                        .mapToLong(PRIMES::get)
                        .boxed()
                        .collect(Collectors.toList());
            }
        }
        for (long p = PRIMES.get(PRIMES.size() - 1) + 2; p <= x; p += 2) {
            if (isPrime(p)) {
                PRIMES.add(p);
            }
        }
        savePrimes();
        return PRIMES.stream()
                .mapToLong(v -> v)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Retrieve the first {@code n} prime numbers.
     * @param n length of returned list
     * @return list of first {@code n} prime numbers
     */
    static List<Long> getFirstPrimes(final int n) {
        if (n < 1) {
            return new ArrayList<>();
        }
        if (n < 2) {
            final List<Long> list = new ArrayList<>();
            list.add(2L);
            return list;
        }
        loadPrimesIfNeeded();
        if (PRIMES.size() < n) {
            for (long p = PRIMES.get(PRIMES.size() - 1) + 2; PRIMES.size() < n; p++) {
                if (isPrime(p)) {
                    PRIMES.add(p);
                }
            }
            savePrimes();
        }
        return IntStream.range(0, n)
                .mapToLong(PRIMES::get)
                .boxed()
                .collect(Collectors.toList());
    }

    static Map<Long, Integer> getPrimeMultiplicities(final long x) {
        final Map<Long, Integer> primeMultiplicities = new HashMap<>();
        if (isPrime(x)) {
            primeMultiplicities.put(x, 1);
            return primeMultiplicities;
        }
        final List<Long> primes = Prime.getPrimesBelow(x / 2);
        long q = x;
        for (long prime : primes) {
            if (q == 1L) {
                break;
            }
            while (q % prime == 0) {
                if (primeMultiplicities.containsKey(prime)) {
                    final int multiplicity = primeMultiplicities.get(prime);
                    primeMultiplicities.put(prime, multiplicity + 1);
                } else {
                    primeMultiplicities.put(prime, 1);
                }
                q /= prime;
            }
        }
        return primeMultiplicities;
    }
}
