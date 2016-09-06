package com.linkmon.componentmodel;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.componentmodel.gameobject.ObjectId;
import com.linkmon.componentmodel.gamesave.JsonSaver;
import com.linkmon.componentmodel.gamesave.AESEncryptor;
import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;
import com.linkmon.componentmodel.items.FoodComponent;
import com.linkmon.componentmodel.items.ItemComponent;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonInputComponent;
import com.linkmon.componentmodel.linkmon.LinkmonPhysicsComponent;
import com.linkmon.componentmodel.linkmon.LinkmonStatusComponent;
import com.linkmon.componentmodel.linkmon.LinkmonTimerComponent;
import com.linkmon.componentmodel.linkmon.poop.PoopInputComponent;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;

public class Player {
	
	private GameObject linkmon;
	
	private int gold;
	private String name;
	
	private List<GameObject> items;
	private List<GameObject> itemsRemoveQueue;
	
	private EventManager eManager;
	
	public Player() {
		
	}
	
	public void addeManager(EventManager eManager) {
		this.eManager = eManager;
	}
	
	public Player(EventManager eManager, int eggChoice) {
		linkmon = ObjectFactory.getInstance().createLinkmon(eggChoice);
		
		gold = 15000;
		
		name = "Kilst";
		
		items = new ArrayList<GameObject>();
		itemsRemoveQueue = new ArrayList<GameObject>();
		
		this.eManager = eManager;
		
		GameObject item = ObjectFactory.getInstance().getObjectFromId(ObjectId.MEAT);
		((ItemComponent)item.getExtraComponents()).setQuantity(1);
		
		addItem(item);
	}

	public GameObject getLinkmon() {
		// TODO Auto-generated method stub
		return linkmon;
	}
	
	public void feedLinkmon(GameObject item) {
		if(items.contains(item)) {
			((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().addHungerLevel(((FoodComponent)item.getExtraComponents()).getFeedAmount());
			itemsRemoveQueue.add(item);
		}
		
		removeItems();
	}
	
	public void buyItem(GameObject item) {
		// TODO Auto-generated method stub
		int price = ((ItemComponent)item.getExtraComponents()).getPrice()*((ItemComponent)item.getExtraComponents()).getQuantity();
		if(gold >= price) {
			addItem(item);
			removeGold(price);
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Bought item: " + item.getName() + " x" + ((ItemComponent)item.getExtraComponents()).getQuantity(), false));
		}
	}
	
	private void removeItems() {
		for(GameObject item : itemsRemoveQueue) {
			((ItemComponent)item.getExtraComponents()).setQuantity(((ItemComponent)item.getExtraComponents()).getQuantity()-1);
			if(((ItemComponent)item.getExtraComponents()).getQuantity() < 1)
				items.remove(item);
		}
		
		itemsRemoveQueue.clear();
	}
	
	public void addItem(GameObject newItem) {
		int amount = ((ItemComponent)newItem.getExtraComponents()).getQuantity();
		boolean match = false;
			for(GameObject item : items) {
				if(item.getId() == newItem.getId()) {
					((ItemComponent)item.getExtraComponents()).add(amount);
					match = true;
				}
			}
			
		if(!match)
			items.add(newItem);
	}

	private void addGold(int amount) {
		// TODO Auto-generated method stub
		gold += amount;
		linkmon.getWorld().geteManager().notify(new ViewEvent(ViewEvents.UPDATE_GOLD, gold));
	}
	
	private void removeGold(int amount) {
		// TODO Auto-generated method stub
		gold -= amount;
		linkmon.getWorld().geteManager().notify(new ViewEvent(ViewEvents.UPDATE_GOLD, gold));
	}

	public int getGold() {
		// TODO Auto-generated method stub
		return gold;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public List<GameObject> getItems() {
		// TODO Auto-generated method stub
		return items;
	}

	public void setName(String playerName) {
		// TODO Auto-generated method stub
		name = playerName;
	}

	public void setGold(int playerGold) {
		// TODO Auto-generated method stub
		gold = playerGold;
	}

	public void setLinkmon(GameObject savedLinkmon) {
		// TODO Auto-generated method stub
		this.linkmon = savedLinkmon;
	}
}
