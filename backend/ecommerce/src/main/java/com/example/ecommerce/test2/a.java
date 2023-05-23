package com.example.ecommerce.test2;

public class a {
	int m = 1;

	public int getM() {
		Integer i = new Integer(8);
		return m;
	}
//	class b extends Integer
	public static void main(String[] args) {
		a a = new b();
		System.out.println(a.getM());
		char d = (char) -98;
		System.out.println(d);
		byte b = 119 + 0;
	}
}

class b extends a {
//	int m = 3;
	public int getM() {
		return m;
	}
}
