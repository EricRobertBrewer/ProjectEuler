package com.uberprinny.projecteuler.problems;

/**
 * Largest palindrome product
 * Problem 4
 * A palindromic number reads the same both ways.
 * The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
 * Find the largest palindrome made from the product of two 3-digit numbers.
 * @author ebrewer
 *
 */
public class Problem4 {

	public static void printAnswer() {
		int p = -1;
		for (int i = 999; i > 99 && (p == -1 || i * i > p); i--) {
			for (int j = i; j > 99 && (p == -1 || i * j > p); j--) {
				if (isPalindrome(i * j)) {
					p = i * j;
				}
			}
		}
		System.out.println(p);
	}
	
	public static boolean isPalindrome(int n) {
		String s = String.valueOf(n);
		for (int i = 0; i < s.length()/2; i++) {
			if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
				return false;
			}
		}
		return true;
	}
}
