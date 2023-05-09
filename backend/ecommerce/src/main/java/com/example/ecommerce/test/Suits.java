package com.example.ecommerce.test;

public enum Suits {
	CLUBS(20), DIAMONDS(20), HEARTS(30), SPADES(30), NOTRUMP(40) {
		public int getValue(int bid) {
			return ((bid - 1) * 30) + 40;
		}
	};

	Suits(int points) {
		this.points = points;
	}

	private int points;

	public int getValue(int bid) {
		return points * bid;
	}
}
