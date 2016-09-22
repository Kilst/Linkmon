package com.linkmon.model.gamesave;

import com.linkmon.model.Player;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.items.ItemComponent;
import com.linkmon.model.linkmon.BirthDate;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.LinkmonStatusComponent;
import com.linkmon.model.linkmon.poop.PoopComponent;

public class GameSave {
	
	// Player info
	
	public String playerName;
	public int playerGold;
	
//	// Player Items
	public int[][] items;
	
	// Linkmon
	
	public int move1;
	public int move2;
	
	public int id;
	
	public int hungerLevel;
	public int exhaustionLevel;
	public boolean isSick; // From not cleaning poops, making pet exhausted, staying hungry too long etc
	public int careMistakes; // From not cleaning poops, making pet exhausted, staying hungry too long etc
	public int happiness; // Not sure what to do with this, just an idea
	
	
	// Poops
	
	public long lastPooped;
	
//	public float[] poopsX;
	
	// Linkmon Stats
	
	public int health;
	public int attack;
	public int defense;
	public int speed;
	
	// Birthdate
	
	public int hour;
	public int minute;
	public int day;
	public int month;
	public int year;
	public long nano;
	
	public long savedTime;
	
	public GameSave() {
		
	}
	
	public GameSave(Player player) {
		
		// Player stats
		
		this.playerName = player.getName();
		this.playerGold = player.getGold();
		
		// Player Items
		
		this.items = new int[player.getItems().size()][2];
		
		for(int i = 0; i < player.getItems().size(); i++) {
			this.items[i][0] = player.getItems().get(i).getId();
			this.items[i][1] = ((ItemComponent)player.getItems().get(i).getExtraComponents()).getQuantity();
		}
		
		// Linkmon
		
		this.move1 = 1;
		this.move2 = 2;
		
		this.id = player.getLinkmon().getId();
		
		LinkmonStatusComponent status = ((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStatus();
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStats();
		PoopComponent poops = ((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getPoopComponent();
		
		this.hungerLevel = status.getHungerLevel();
		this.exhaustionLevel = 0;
		this.isSick = status.isSick(); // From not cleaning poops, making pet exhausted, staying hungry too long etc
		this.careMistakes = status.getCareMistakes(); // From not cleaning poops, making pet exhausted, staying hungry too long etc
		this.happiness = 100; // Not sure what to do with this, just an idea
		
		// Poops
		
//		for(int i = 0; i < poops.getPoopList().size(); i++) {
//			this.poopsX[i] = poops.getPoopList().get(i).getX();
//		}
		
		this.lastPooped = poops.getLastPooped();
		
		// Linkmon Stats
		
		this.health = stats.getHealth();
		this.attack = stats.getAttack();
		this.defense = stats.getDefense();
		this.speed = stats.getSpeed();
		
		// Birthdate
		
		this.hour = status.getBirthDate().getHour();
		this.minute = status.getBirthDate().getMinute();
		this.day = status.getBirthDate().getDay();
		this.month = status.getBirthDate().getMonth();
		this.year = status.getBirthDate().getYear();
		this.nano = status.getBirthDate().getNano();
		
		this.savedTime = System.nanoTime();
	}
	
	public Player getSavedPlayer() {
		Player player = new Player();
		player.setName(this.playerName);
		player.setGold(this.playerGold);
		
		player.setSavedItems(items);
		
		player.setLinkmon(getSavedLinkmon());
		
		return player;
	}
	
	private GameObject getSavedLinkmon() {
		GameObject linkmon = ObjectFactory.getInstance().createLinkmon(id);
		LinkmonStatusComponent status = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus();
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		PoopComponent poops = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getPoopComponent();
		
		status.setBirthDate(new BirthDate(hour, minute ,day ,month , year, nano));
		
		status.setHungerLevel(hungerLevel);
		status.setSick(isSick);
		status.setCareMistakes(careMistakes);
		
		stats.setAttack(attack);
		stats.setDefense(defense);
		stats.setHealth(health);
		stats.setSpeed(speed);
		
//		ArrayList<GameObject> poopList = new ArrayList<GameObject>();
//		
//		for(int i = 0; i < 3; i++) {
//			GameObject poop = ObjectFactory.getInstance().getObjectFromId(ObjectId.POOP);
//			poop.setX(poopsX[i]);
//			poopList.add(poop);
//		}
//		
//		poops.setPoopList(poopList);
		
		return linkmon;
	}
}
