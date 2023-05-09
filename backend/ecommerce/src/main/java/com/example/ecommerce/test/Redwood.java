package com.example.ecommerce.test;

strictfp abstract interface ab {
	void runme();
}

class Clidder {
	private final void flipper() {
		System.out.println("Clidder");
	}
}

// class Redwood extends Clidder {
//	public final void flipper() {
//		System.out.println("Clidlet");
//	}

//	public static void main(String[] args) {
//		new a().flipper();
//	}
//}

class m {
	boolean[] b[][] = new boolean[7][7][8];
}

enum c implements ab {
	;
	void ru(int... b) {

	}

	@Override
	public void runme() {
		// TODO Auto-generated method stub
		ru();
		ru(1);
		ru(1, 2);
	}
}

class Electronic implements Device {
	public void doIt() {
	}
}

class Phone3 extends Electronic implements Device {
	public void doIt() {
		super.doIt();
	}
}

interface Device {
	public void doIt();
}

public class Redwood extends Tree {
	public static void main(String[] args) {
		new Redwood().go();
	}

	void go() {
		go2(new Tree(), new Redwood());
		go2((Redwood) new Tree(), new Redwood());
	}

	void go2(Tree t1, Redwood r1) {
		Redwood r2 = (Redwood) t1;
		Tree t2 = (Tree) r1;
	}
}

class Tree {
}
