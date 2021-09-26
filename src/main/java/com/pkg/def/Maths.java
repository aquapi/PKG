package com.pkg.def;

import java.util.ArrayList;
import java.util.List;

public class Maths {
	private Maths() {
	}

	/**
	 * @param a
	 * @param b
	 * @return greatest common divisor of 2 imput numbers
	 * @since 1.0
	 */

	public static long gcd(long a, long b) {
		return (b == 0) ? a : gcd(b, a % b);
	}

	/**
	 * @param a
	 * @param b
	 * @return least common multiple of 2 input number
	 * @since 1.0
	 */

	public static long lcm(long a, long b) {
		return (a * b) / gcd(a, b);
	}

	/**
	 * @param position
	 * @return fibonacci number related to its position in the serial
	 * @since 1.0
	 */

	public static long fibonacciNumbers(long position) {
		return (position == 0 || position == 1) ? 1 : fibonacciNumbers(position - 1) + fibonacciNumbers(position - 2);
	}

	/**
	 * @param position
	 * @return prime number related to its position in the serial
	 * @since 1.0
	 */

	public static long primeNumbers(long position) {
		position--;
		if (position == 0)
			return 2;
		else if (position == 1)
			return 3;
		else if (position < 0)
			return -1;
		else {
			long p = 0;
			long n = 2;
			while (p < position) {
				n++;
				if (Maths.isPrimeNumber(n))
					p++;
			}
			return n;
		}
	}

	/**
	 * @param n
	 * @return true or false if a number is a prime number
	 * @since 1.0
	 */

	public static boolean isPrimeNumber(long n) {
		if (n < 2)
			return false;
		long squareRoot = (long) Math.sqrt(n);
		for (long i = 2; i <= squareRoot; i++)
			if (n % i == 0)
				return false;
		return true;
	}

	/**
	 * @param x
	 * @return 1 * 2 * 3 * ... * x
	 * @since 1.0
	 */

	public static long factor(long x) {
		return x == 1 ? 1 : factor(x - 1) * x;
	}

	/**
	 * @param x
	 * @return 1 + 2 + 3 + ... + x
	 * @since 1.6
	 */

	public static long sumFrom(long x) {
		return x == 1 ? 1 : sumFrom(x - 1) + x;
	}

	/**
	 * @param i is a square num?
	 * @return true or false if i is a square number
	 * @since 1.0
	 */

	public static boolean isSquare(long i) {
		long a = (long) Math.sqrt(i);
		return (a * a == i);
	}

	/**
	 * @param n = x * y * ....
	 * @return analyse number and send them to a list
	 * @since 1.0
	 */

	public static List<Integer> analysis(int n) {
		int i = 2;
		List<Integer> listNumbers = new ArrayList<Integer>();
		while (n > 1) {
			if (n % i == 0) {
				n = n / i;
				listNumbers.add(i);
			} else
				i++;
		}
		if (listNumbers.isEmpty())
			listNumbers.add(n);
		return listNumbers;
	}

	/**
	 * @param base the base
	 * @param v    base ^ x = v
	 * @return exponent of 2 input numbers
	 * @since 1.0
	 */

	public static double log(double base, double v) {
		// logbase(v)
		return v == 1 ? 0
				: (v > 0 ? (Math.log(v) / Math.log(base)) : (base == 0 && v > 0 ? Constants.posiInf : Constants.NaN));
	}

	/**
	 * @param exponent the exponent
	 * @param v        the power of 'exponent' of root(exponent, v)
	 * @param round    if true, round the number to integer format
	 * @return base of 2 input numbers
	 * @since 1.0
	 */

	public static double root(double exponent, double v, boolean round) {
		double n = 1d / exponent;
		return v == 0 ? 0d : (v == 1d ? 1d : (exponent == 0d ? 1d : (round) ? Math.round(Math.pow(v, n)) : Math.pow(v, n)));
	}

	/**
	 * @param exponent the exponent
	 * @param v        the power of 'exponent' of root(exponent, v)
	 * @return base of 2 input numbers
	 * @since 1.0
	 */

	public static double root(double exponent, double v) {
		return root(exponent, v, false);
	}


	/**
	 * @param x
	 * @return true if x is a number
	 * @since 1.4
	 */

	public static boolean isNaN(Object x) {
		return !(x instanceof Number);
	}

	/**
	 * @param n check whether n is a palindrome or not
	 * @return whether n is a palindrome or not
	 * @since 1.4
	 */

	public static boolean isPalindrome(int n) {
		int sum = 0, r;
		int temp = n;
		while (n > 0) {
			r = n % 10;
			sum = (sum * 10) + r;
			n = n / 10;
		}
		return (temp == sum);
	}

	/**
	 * @param x from
	 * @param y to
	 * @return list of number between x and y
	 * @since 1.8
	 */

	public static int[] range(int x, int y) {
		int[] temp = new int[y - x];
		int h = x;
		while (x < y) {
			temp[x - h] = x;
			x++;
		}
		return temp;
	}

	/**
	 * @param a an angle, in radians.
	 * @return the cotangent of the argument.
	 * @since 1.8
	 */

	public static double cot(double a) {
		return 1d / Math.tan(a);
	}

	/**
	 * @param a the value whose arc tangent is to be returned.
	 * @return the arc cotangent of the argument.
	 * @since 1.8
	 */

	public static double acot(double a) {
		return Math.atan(1d / a);
	}
}
