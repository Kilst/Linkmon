package com.linkmon.model;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.items.ItemComponent;
import com.linkmon.model.items.UsableItemComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.view.screens.widgets.messages.MessageType;

public class Player {
	
	private GameObject linkmon;
	
	private int gold;
	private String name;
	
	private List<GameObject> items;
	private List<GameObject> itemsRemoveQueue;
	
	private EventManager eManager;
	
	private World world;
	
	private long lastGiftTime;
	
	private int giftId = -1; // no opengl context from network. since objects get created with textures
	
	public Player() {
		gold = 15000;
		
		name = "Kilst";
		
		items = new ArrayList<GameObject>();
		itemsRemoveQueue = new ArrayList<GameObject>();
	}
	
	public void update() {
		if(giftId != -1) {
			GameObject item = ObjectFactory.getInstance().getObjectFromId(giftId);
			addItem(item);
			giftId = -1;
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, MessageType.GAME_MESSAGE,  "Got gift: " + item.getName() + " x" + ((ItemComponent)item.getExtraComponents()).getQuantity()));
		}
	}
	
	public void addeManager(EventManager eManager) {
		this.eManager = eManager;
	}
	
	public Player(EventManager eManager, int eggChoice, World world) {
		linkmon = ObjectFactory.getInstance().createLinkmon(eggChoice);
		
		this.world = world;
		
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
	
	public void buyItem(GameObject item) {
		// TODO Auto-generated method stub
		int price = ((ItemComponent)item.getExtraComponents()).getPrice()*((ItemComponent)item.getExtraComponents()).getQuantity();
		if(gold >= price) {
			addItem(item);
			removeGold(price);
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, MessageType.GAME_MESSAGE, "Bought item: " + item.getName() + " x" + ((ItemComponent)item.getExtraComponents()).getQuantity()));
		}
	}
	
	private void removeItemsFromQueue() {
		for(GameObject item : itemsRemoveQueue) {
			((ItemComponent)item.getExtraComponents()).setQuantity(((ItemComponent)item.getExtraComponents()).getQuantity()-1);
			if(((ItemComponent)item.getExtraComponents()).getQuantity() < 1)
				items.remove(item);
		}
		
		itemsRemoveQueue.clear();
	}
	
	private void removeItem(GameObject item) {
		for(GameObject itemObject : items) {
			if(itemObject.getId() == item.getId()) {
				itemsRemoveQueue.add(itemObject);
			}
		}
		removeItemsFromQueue();
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
	
	public void useItem(GameObject item) {
		if(((ItemComponent)item.getExtraComponents()).use(item,getLinkmon(), getWorld()))
			removeItem(item);
	}

	private void addGold(int amount) {
		// TODO Auto-generated method stub
		gold += amount;
		linkmon.getWorld().geteManager().notify(new ViewEvent(ViewEvents.UPDATE_GOLD, gold));
	}
	
	private void removeGold(int amount) {
		// TODO Auto-generated method stub
		gold -= amount;
		linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.UPDATE_GOLD, gold));
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

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void setSavedItems(int[][] savedItems) {
		// TODO Auto-generated method stub
		for(int i = 0; i < savedItems.length; i++) {
			GameObject item = ObjectFactory.getInstance().getObjectFromId(savedItems[i][0]);
			((ItemComponent)item.getExtraComponents()).setQuantity(savedItems[i][1]);
			items.add(item);
		}
	}
	
	public boolean checkGiftTime() {
		if(System.currentTimeMillis() - lastGiftTime > 50000) {
			lastGiftTime = System.currentTimeMillis();
			return true;
		}
		else {
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE,MessageType.GAME_MESSAGE,  "Can't do that yet!\n" + (50000-(System.currentTimeMillis() - lastGiftTime))/1000 + " seconds left"));
			return false;
		}
	}

	public long getLastGiftTime() {
		return lastGiftTime;
	}

	public void receiveGift(int itemId) {
		// TODO Auto-generated method stub
		giftId = itemId;
		
	}

	public void receiveRewards(int[] rewards) {
		// TODO Auto-generated method stub
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		stats.setHealth(stats.getHealth()+rewards[0]);
		stats.setAttack(stats.getAttack()+rewards[1]);
		stats.setDefense(stats.getDefense()+rewards[2]);
		stats.setSpeed(stats.getSpeed()+rewards[3]);
		addGold(rewards[4]);
	}
}
