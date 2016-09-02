package com.linkmon.model.gameobject.linkmon;

public class LinkmonStats {
	
	private int health;
	private int attack;
	private int defense;
	private int speed;
	
	private int addedHealth;
	private int addedAttack;
	private int addedDefense;
	private int addedSpeed;
	
	private Linkmon linkmon;
	
	public LinkmonStats() {
		
	}
	
	public LinkmonStats(Linkmon linkmon, int health, int attack, int defense, int speed) {
		this.linkmon = linkmon;
		this.health = (health);
		this.attack=(attack);
		this.defense=(defense);
		this.speed=(speed);
	}
	
	public void addStats(int addedHealth, int addedAttack, int addedDefense, int addedSpeed) {
		this.addedHealth = addedHealth;
		this.addedAttack = addedAttack;
		this.addedDefense = addedDefense;
		this.addedSpeed = addedSpeed;
		
		linkmon.rankCheck();
	}
	
	public void update() {
		this.setHealth(this.getHealth() + this.addedHealth);
		this.setAttack(this.getAttack() + this.addedAttack);
		this.setDefense(this.getDefense() + this.addedDefense);
		this.setSpeed(this.getSpeed() + this.addedSpeed);
		
		this.addedHealth = 0;
		this.addedAttack = 0;
		this.addedDefense = 0;
		this.addedSpeed = 0;
		
		linkmon.rankCheck();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		linkmon.rankCheck();
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
		linkmon.rankCheck();
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
		linkmon.rankCheck();
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		linkmon.rankCheck();
	}

	public int getAddedHealth() {
		return addedHealth;
	}

	public void setAddedHealth(int addedHealth) {
		this.addedHealth = addedHealth;
	}

	public int getAddedAttack() {
		return addedAttack;
	}

	public void setAddedAttack(int addedAttack) {
		this.addedAttack = addedAttack;
	}

	public int getAddedDefense() {
		return addedDefense;
	}

	public void setAddedDefense(int addedDefense) {
		this.addedDefense = addedDefense;
	}

	public int getAddedSpeed() {
		return addedSpeed;
	}

	public void setAddedSpeed(int addedSpeed) {
		this.addedSpeed = addedSpeed;
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

	public void addLinkmon(Linkmon linkmon) {
		// TODO Auto-generated method stub
		this.linkmon = linkmon;
	}
}
