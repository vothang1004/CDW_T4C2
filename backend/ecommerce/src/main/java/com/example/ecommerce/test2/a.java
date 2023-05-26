package com.example.ecommerce.test2;

import java.io.Console;
import java.io.IOException;

public class a {
	int m = 1;

	public int getM() {
		Integer i = new Integer(8);
		String x = "1123";
		x.length();
		Console c = System.console();
		c.readPassword();
		System.out.println();
//		System.in.
		throw new IOException();
		return m;
	}

	private static final void go(Long x) {
		System.out.println("Long");
	}

	static enum m {
		a
	}

	class Animal {

	}

	class Dog extends Animal {

	}

	class Cat extends Animal {

	}

	void doStuff() throws IOException {
		doMore();
	}

	class TestEx {
		static Integer i;
		final {
			i = 3;
		}

		public void main(String[] args) {
			boolean b = false;
			assert (b = true);
			String s = new String("sab");
			s = s.concat("huhu");
			badMethod();
		}

		 void badMethod() { // No need to declare an Error
			doStuff();
		}

		void doStuff() { // No need to declare an Error
			try {
				throw new Error();
			} catch (Error me) {
				throw me; // We catch it, but then rethrow it
			}
		}
	}

	void doMore() {
		throw new IndexOutOfBoundsException();
	}

//	class b extends Integer
	public static void main(String[] args) throws ArithmeticException {
		run();
		methodA(-1);
		for (int y = 0, z = 2; y < 3; y++) {
			System.out.println("run y " + y);
		}
	}

	public static void run() {
		int x = 3;
		int y = 1;
		assert (y > x) : "y is " + y + " x is " + x;
		System.out.println("doseomthing");
	}

	private static void methodA(int num) {
		assert false; // throws an AssertionError
		assert 7 == 3;
		assert badTrick();
		// if this test isn't true
//		useNum(num + 8);
		System.out.println("run assum num is positive");
	}

	public static boolean badTrick() {
		System.out.println("modify");
		return true;
	}
}

class b extends a {
//	int m = 3;
	public int getM() {
		return m;
	}
}
