package com.linkmon.model;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonMoveComponent;
import com.linkmon.model.linkmon.LinkmonStatsComponent;

public class CryoLinkmon {
	
	private int id;

	private int health;
	private int attack;
	private int defense;
	private int speed;
	
	private int move1;
	private int move2;
	private int move3;
	private int rank;
	
	private EventManager eManager;
	
	public CryoLinkmon(int linkmonId, int attack, int defense, int speed, int move1Id, int move2Id, int health) {
		this.id = linkmonId;
		this.attack = (attack);
		this.defense = (defense);
		this.speed = (speed);
		this.move1 = (move1Id);
		this.move2 = (move2Id);
		this.health = health;
	}
	
	public int getLinkmonId() {
		return id;
	}
	
	public CryoLinkmon(GameObject linkmon) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		LinkmonMoveComponent moves = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getMoves();
		this.id = linkmon.getId();
		this.health = stats.getHealth();
		this.attack = stats.getAttack();
		this.defense = stats.getDefense();
		this.speed = stats.getSpeed();
		this.move1 = moves.getMove1();
		this.move2 = moves.getMove2();
		this.move3 = moves.getMove3();
		this.rank = stats.getRank();
		eManager = linkmon.getWorld().geteManager();
	}
	
	public int getHealth() {
		return health;
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
	
	public int getMove3() {
		return move3;
	}
	
	@Override
	public String toString() {
		return "Fireboy " + id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
