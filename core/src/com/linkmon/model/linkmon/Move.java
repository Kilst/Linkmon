package com.linkmon.model.linkmon;

public class Move {
	private int id;
	private int elementalType;
	private int slot;
	private int damage;
	private int ignoreDamage;
	private int energy;
	private String name;
	
	private int type;
	
	public Move(int id, int elementalType, int slot, int damage, int ignoreDamage, int energy, String name, int type) {
		this.id = id;
		this.elementalType = elementalType;
		this.slot = slot;
		this.damage = damage;
		this.ignoreDamage = ignoreDamage;
		this.energy = energy;
		this.name = name;
		this.type = type;
	}


	public int getId() {
		return id;
	}

	public int getElementalType() {
		return elementalType;
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


	public int getSlot() {
		// TODO Auto-generated method stub
		return slot;
	}


	public int getType() {
		return type;
	}
}
