package com.linkmon.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.model.gameobject.items.ItemFactory;
import com.linkmon.model.gameobject.items.ItemIds;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.model.gameobject.linkmon.Linkmon;
import com.linkmon.model.gameobject.linkmon.LinkmonIds;

public class Player {
	
	private Linkmon linkmon;
	private BattleLinkmon battleLinkmon;
	
	private BattleLinkmon savedLinkmon;
	private BattleLinkmon extraSavedLinkmon;
	
	private String name;
	
	private int gold;
	
	private int trainerRank;
	
	private List<Item> itemsList;
	
	private EventManager eManager;
	
	private World world;
	
	private long giftTime = 0;
	
	public Player() {
		
	}
	
	public Player(EventManager eManager, World world) {
		this.world = world;
		this.eManager = eManager;
		linkmon = new Linkmon(LinkmonIds.FIRE_BABY, 0, 45, eManager);
		world.addLinkmonToWorld(linkmon);
		gold = 15000;
		name = "Kilst";
		itemsList = new ArrayList<Item>();
	}
	
	public Player(String playerName, int eggChoice, EventManager eManager, World world) {
		this.world = world;
		this.eManager = eManager;
		linkmon = new Linkmon(eggChoice, 0, 45, eManager);
		world.addLinkmonToWorld(linkmon);
		gold = 15000;
		name = playerName;
		itemsList = new ArrayList<Item>();
	}

	public Linkmon getLinkmon() {
		return linkmon;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public int getGold() {
		// TODO Auto-generated method stub
		return gold;
	}

	public void setLinkmon(Linkmon loadLinkmonSave) {
		// TODO Auto-generated method stub
		linkmon = loadLinkmonSave;
	}

	public BattleLinkmon getBattleLinkmon() {
		return battleLinkmon;
	}

	public void createBattleLinkmon() {
		this.battleLinkmon = new BattleLinkmon(linkmon);
	}

	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return itemsList;
	}
	

	public World getWorld() {
		return world;
	}

	public void removeGold(int amount) {
		// TODO Auto-generated method stub
		this.gold -= amount;
	}
	
	public void addGold(int amount) {
		// TODO Auto-generated method stub
		this.gold += amount;
	}

	public BattleLinkmon getSavedLinkmon() {
		return savedLinkmon;
	}

	public void setSavedLinkmon(BattleLinkmon savedLinkmon) {
		this.savedLinkmon = savedLinkmon;
	}

	public void setGiftTime(long nanoTime) {
		// TODO Auto-generated method stub
		giftTime = nanoTime;
	}
	
	public long getGiftTime() {
		// TODO Auto-generated method stub
		return giftTime;
	}

	public void setItems(ArrayList<Item> savedItems) {
		// TODO Auto-generated method stub
		this.itemsList = savedItems;
	}

	public void seteManager(EventManager eManager) {
		// TODO Auto-generated method stub
		this.eManager = eManager;
	}

	public void updateLoad() {
		// TODO Auto-generated method stub
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_GOLD, getGold()));
	}
}
