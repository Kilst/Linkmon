package com.linkmon.componentmodel.linkmon;

public class Move {
	
	private int id;
	private int type;
	private int damage;
	private String name;
	
	public Move(int id, int type, int damage, String name) {
		this.id = id;
		this.type = type;
		this.damage = damage;
		this.name = name;
	}


	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public int getDamage() {
		return damage;
	}


	public String getName() {
		return name;
	}
}
