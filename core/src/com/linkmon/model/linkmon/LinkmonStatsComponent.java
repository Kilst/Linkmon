package com.linkmon.model.linkmon;

import com.linkmon.model.components.IStatsComponent;
import com.linkmon.model.gameobject.GameObject;

public class LinkmonStatsComponent implements IStatsComponent {
	
	private int maxHealth = 0;
	private int health;
	private int attack;
	private int defense;
	private int speed;
	
	private int addedHealth;
	private int addedAttack;
	private int addedDefense;
	private int addedSpeed;
	
	private int rank;
	
	public boolean updated = true;
	
	public LinkmonStatsComponent() {
		health = 100;
		attack = 10;
		defense = 10;
		speed = 10;
		
		maxHealth = health;
	}
	
	public LinkmonStatsComponent(int health, int attack, int defense, int speed) {
		health = 500;
		attack = 22;
		defense = 16;
		speed = 33;
		
		maxHealth = health;
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		if(updated) {
			this.setHealth(this.getHealth() + this.addedHealth);
			this.setAttack(this.getAttack() + this.addedAttack);
			this.setDefense(this.getDefense() + this.addedDefense);
			this.setSpeed(this.getSpeed() + this.addedSpeed);
			
			this.addedHealth = 0;
			this.addedAttack = 0;
			this.addedDefense = 0;
			this.addedSpeed = 0;
			
			//object.rankCheck();
			maxHealth = health;
			updated = false;
		}
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAddedHealth() {
		return addedHealth;
	}

	public void addHealth(int addedHealth) {
		this.health += addedHealth;
	}

	public int getAddedAttack() {
		return addedAttack;
	}

	public void addAttack(int addedAttack) {
		this.attack += addedAttack;
	}

	public int getAddedDefense() {
		return addedDefense;
	}

	public void addDefense(int addedDefense) {
		this.defense += addedDefense;
	}

	public int getAddedSpeed() {
		return addedSpeed;
	}

	public void addSpeed(int addedSpeed) {
		this.speed += addedSpeed;
	}

	public int rankCheck() {
		// TODO Auto-generated method stub
		int total = health + attack + defense + speed;
		if(total < 200)
			return RankIds.E;
		else if(total < 400)
			return RankIds.D;
		else if(total < 700)
			return RankIds.C;
		else if(total < 900)
			return RankIds.B;
		else if(total < 1500)
			return RankIds.A;
		else
			return RankIds.S;
	}

	public int getRank() {
		// TODO Auto-generated method stub
		return rank;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
}
