package com.linkmon.model.gameobject.linkmon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.linkmon.helpers.ResourceLoader;

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
	private boolean crit;
	private int effectiveness;
	private boolean dodge;
	
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
	
	public BattleLinkmon(Linkmon linkmon) {
		this.id = linkmon.getId();
		this.health = linkmon.getStats().getHealth();
		this.attack = (linkmon.getStats().getAttack());
		this.defense = (linkmon.getStats().getDefense());
		this.speed = linkmon.getStats().getSpeed();
		this.move1 = linkmon.getMove1();
		this.move2 = linkmon.getMove2();
		this.playerName = "";
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

	public boolean isCrit() {
		return crit;
	}

	public void setCrit(boolean crit) {
		this.crit = crit;
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
}
