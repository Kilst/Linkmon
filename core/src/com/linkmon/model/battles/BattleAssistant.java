package com.linkmon.model.battles;

import com.linkmon.eventmanager.EventManager;

public class BattleAssistant {
	
	public boolean updated;
	
	private int id;

	private int health;
	private int attack;
	private int defense;
	private int speed;
	
	private int move1;
	private int move2;
	
	private int energy = 25;
	
	private final int maxEnergy = 50;
	private int rank;
	
	private EventManager eManager;

}
