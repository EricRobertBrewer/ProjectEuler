package com.ericrobertbrewer.projecteuler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.LongStream;

public class Solution {

	public static void main(String[] args) {
		if (args.length < 1 || args.length > 1) {
			throw new IllegalArgumentException("Usage: <problem-number>");
		}
		final int number = Integer.parseInt(args[0]);
		final long answer = getAnswer(number);
		System.out.println(answer);
	}

	private static long getAnswer(int number) {
        if (number == 1) {
            int sum = 0;
            for (int i = 1; i < 1000; i++) {
                if (i % 3 == 0 || i % 5 == 0) {
                    sum += i;
                }
            }
            return sum;

        } else if (number == 2) {
            final ArrayList<Integer> fib = new ArrayList<>();
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
            return sum;

        } else if (number == 3) {
            final List<Long> primes = new ArrayList<>();
            long n = 600851475143L;
            primes.add(2L);
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

        } else if (number == 4) {
            int p = -1;
            for (int i = 999; i > 99 && (p == -1 || i * i > p); i--) {
                for (int j = i; j > 99 && (p == -1 || i * j > p); j--) {
                    if (Utility.isPalindrome(i * j)) {
                        p = i * j;
                    }
                }
            }
            return p;

        } else if (number == 5) {
            final long N = 20;
            // Initialize list of primes up to N, and map of prime factors from 2 -> N
            final List<Long> primes = Prime.getPrimesBelow(N);
            HashMap<Long, Integer> factors = new HashMap<>();
            for (long prime : primes) {
                factors.put(prime, 0);
            }

            // Count prime factors of each number from 2 -> N
            for (long i = N; i >= N/2; i--) {
                long product = i; // Product of some primes; continually divide it (if divisible) by primes found in list of primes
                for (long prime : primes) {
                    int count = 0; // Order of magnitude of this prime as a factor of product
                    while (product > 1 && product % prime == 0) { // if divisible...
                        count++;
                        if (count > factors.get(prime)) {
                            factors.put(prime, count); // Also increment factor count if it exceeds previous count
                        }
                        product /= prime;
                    }
                }
            }

            long result = 1;
            for (long prime : primes) {
                for (int i = 0; i < factors.get(prime); i++) {
                    result *= prime;
                }
            }
            return result;

        } else if (number == 6) {
            final int n = 100;
            final long sumOfSquares = LongStream.range(1, n + 1)
                    .map(x -> x * x)
                    .sum();
            final long sum = LongStream.range(1, n + 1).sum();
            final long squareOfSum = sum * sum;
            return squareOfSum - sumOfSquares;

        } else if (number == 7) {
            final int n = 10001;
            final List<Long> primes = Prime.getFirstPrimes(n);
            return primes.get(n - 1);

        } else if (number == 8) {
            final String num = "7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450";
            final long[] digits = num.chars()
                    .map(c -> c - '0')
                    .asLongStream()
                    .toArray();
            final int adjacent = 13;
            long max = -1L;
            for (int i = adjacent; i < digits.length; i++) {
                final long product = Arrays.stream(digits, i - adjacent, i)
                        .reduce(1L, (prev, x) -> x * prev);
                if (product > max) {
                    max = product;
                }
            }
            return max;

        } else if (number == 9) {
            for (long c = 2; c < 1000L; c++) {
                final long cSquared = c * c;
                final long aEnd = c / 2L;
                for (long a = 1; a < aEnd; a++) {
                    final long aSquared = a * a;
                    final long bSquared = cSquared - aSquared;
                    if (Utility.isSquare(bSquared)) {
                        final long b = (long) Math.sqrt(bSquared);
                        if (a + b + c == 1000L) {
                            return a * b * c;
                        }
                    }
                }
            }
            return -1L;

        } else if (number == 10) {
            return Prime.getPrimesBelow(2000000).stream().mapToLong(v -> v).sum();

        } else if (number == 11) {
            // Read grid.
            final String[] GRID = {
                    "08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08",
                    "49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00",
                    "81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65",
                    "52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91",
                    "22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80",
                    "24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50",
                    "32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70",
                    "67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21",
                    "24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72",
                    "21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95",
                    "78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92",
                    "16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57",
                    "86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58",
                    "19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40",
                    "04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66",
                    "88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69",
                    "04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36",
                    "20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16",
                    "20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54",
                    "01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48"
            };
            final long[][] grid = new long[GRID.length][20];
            for (int i = 0; i < GRID.length; i++) {
                final String row = GRID[i];
                final String[] elements = row.split(" ");
                for (int j = 0; j < elements.length; j++) {
                    if (elements[j].startsWith("0")) {
                        grid[i][j] = Long.parseLong(elements[j].substring(1));
                    } else {
                        grid[i][j] = Long.parseLong(elements[j]);
                    }
                }
            }
            // Find greatest product of four adjacent numbers.
            long greatest = 0;
            for (int i = 0; i < grid.length - 3; i++) {
                for (int j = 0; j < grid[i].length - 3; j++) {
                    // Right.
                    final long right = grid[i][j] * grid[i][j + 1] * grid[i][j + 2] * grid[i][j + 3];
                    if (right > greatest) {
                        greatest = right;
                    }
                    // Diagonal down.
                    final long diagonalDown = grid[i][j] * grid[i + 1][j + 1] * grid[i + 2][j + 2] * grid[i + 3][j + 3];
                    if (diagonalDown > greatest) {
                        greatest = diagonalDown;
                    }
                    // Down.
                    final long down = grid[i][j] * grid[i + 1][j] * grid[i + 2][j] * grid[i + 3][j];
                    if (down > greatest) {
                        greatest = down;
                    }
                    // Diagonal up.
                    final long diagonalUp = grid[grid.length - i - 1][j] * grid[grid.length - i - 2][j + 1] *
                            grid[grid.length - i - 3][j + 2] * grid[grid.length - i - 4][j + 3];
                    if (diagonalUp > greatest) {
                        greatest = diagonalUp;
                    }
                }
            }
            return greatest;

        } else if (number == 12) {
            int i = 1;
            long triangle;
            int factorCount;
            do {
                triangle = Utility.getTriangleNumber(i);
                factorCount = Utility.getFactorCount(triangle);
                i++;
            } while (factorCount < 500);
            return triangle;

        }
        throw new IllegalArgumentException("No solution provided for problem number `" + number + "`.");
    }
}
