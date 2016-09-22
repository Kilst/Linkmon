package com.linkmon.model.linkmon;

public class Move {
	private int id;
	private int type;
	private int damage;
	private int ignoreDamage;
	private int energy;
	private String name;
	
	public Move(int id, int type, int damage, int ignoreDamage, int energy, String name) {
		this.id = id;
		this.type = type;
		this.damage = damage;
		this.ignoreDamage = ignoreDamage;
		this.energy = energy;
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


	public int getEnergy() {
		// TODO Auto-generated method stub
		return energy;
	}
	
	public int getIgnoreDamage() {
		return ignoreDamage;
}
}
