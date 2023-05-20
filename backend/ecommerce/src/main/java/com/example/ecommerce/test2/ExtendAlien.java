package com.example.ecommerce.test2;

import com.example.ecommerce.test.Alien;

public class ExtendAlien extends Alien {
	enum c implements Runnable{
		
		a, b, c;
		int m;
		

		c(int m) {
			this.m = m;
		}

		c() {

		}

		public void runme() {
			System.out.println("run general" + m);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}

	}
	

	final int a;
	{
		a = 2;
	}

	public ExtendAlien() {
		System.out.println(a);
//		c.c(5);
		Thread t = new Thread(c.c);
		t.start();
		// TODO Auto-generated constructor stub
	}

	int a(int b) {
		return b;
	}

	void b(Alien alien) {

	}

	void b(ExtendAlien alien) {

	}

	int a(Object b) {
		return -1;
	}

	int a(int... b) {
		return 0;
	}

	int a(Integer b) {
		return b + 2;
	}

	void fire() {
		int z = a(2);
		ExtendAlien a = new ExtendAlien();
		b(a);
		Short s = 2;
		
		this.invade(s);
	}

	public static void main(String[] args) {
		Alien a = new ExtendAlien();
		Object b = "runme";
		System.out.println(a.getClass());
	}
//	protected String invade(short ships) {
//		return "a few";
//	}

}
