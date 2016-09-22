package com.linkmon.model.battles;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonStatsComponent;

public class BattleLinkmon {
	
	public boolean updated;
	
	private String playerName;
	
	private int id;

	private int health;
	private int attack;
	private int defense;
	private int speed;
	
	private int move1;
	private int move2;
	
	private int usingMove;
	private int effectiveness;
	private boolean dodge;
	
	private int energy = 25;
	
	private final int maxEnergy = 50;
	
	private EventManager eManager;
	
	public BattleLinkmon(int linkmonId, int attack, int defense, int speed, int move1Id, int move2Id, int health, String playerName) {
		this.id = linkmonId;
		this.attack = (attack);
		this.defense = (defense);
		this.speed = (speed);
		this.move1 = (move1Id);
		this.move2 = (move2Id);
		this.health = health;
		this.playerName = playerName;
	}
	
	public int getLinkmonId() {
		return id;
	}
	
	public BattleLinkmon(GameObject linkmon) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		this.id = linkmon.getId();
		this.health = stats.getHealth();
		this.attack = stats.getAttack();
		this.defense = stats.getDefense();
		this.speed = stats.getSpeed();
		this.move1 = 1;
		this.move2 = 2;
		this.playerName = "";
		eManager = linkmon.getWorld().geteManager();
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getId() {
		return id;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getSpeed() {
		return speed;
	}

	public int getMove1() {
		return move1;
	}

	public int getMove2() {
		return move2;
	}

	public int getUsingMove() {
		return usingMove;
	}

	public void setUsingMove(int usingMove) {
		this.usingMove = usingMove;
	}

	public int getEffectiveness() {
		return effectiveness;
	}

	public void setEffectiveness(int effectiveness) {
		this.effectiveness = effectiveness;
	}

	public boolean isDodge() {
		return dodge;
	}

	public void setDodge(boolean dodge) {
		this.dodge = dodge;
	}
	
	@Override
	public String toString() {
		return "Fireboy " + id;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public boolean checkEnergy(int energy) {
		// TODO Auto-generated method stub
		if(this.energy + energy < 0) {
			eManager.notify(new ModelEvent(ModelEvents.NOT_ENOUGH_ENERGY));
			return false;
		}
		else
			return true;
	}
}
