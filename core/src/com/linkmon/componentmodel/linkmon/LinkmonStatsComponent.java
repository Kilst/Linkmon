package com.linkmon.componentmodel.linkmon;

import com.linkmon.componentmodel.components.IStatsComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.model.gameobject.linkmon.Linkmon;
import com.linkmon.model.gameobject.linkmon.RankIds;
import com.linkmon.model.gameobject.linkmon.StatType;

public class LinkmonStatsComponent implements IStatsComponent {
	
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
		attack = 9;
		defense = 7;
		speed = 8;
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

	public int getRank() {
		// TODO Auto-generated method stub
		return rank;
	}

	public void train(int statType) {
		// TODO Auto-generated method stub
		updated = true;
		
		switch(statType) {
			case StatType.HEALTH : {
				setAddedHealth(5);
				break;
			}
			case StatType.ATTACK : {
				setAddedAttack(5);
				break;
			}
			case StatType.DEFENSE : {
				setAddedDefense(5);
				break;
			}
			case StatType.SPEED : {
				setAddedSpeed(5);
				break;
			}
		}	
	}
}
