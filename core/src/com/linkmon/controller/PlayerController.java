package com.linkmon.controller;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.GameSave;
import com.linkmon.model.Player;
import com.linkmon.model.gameobject.items.Food;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.model.gameobject.items.ItemFactory;
import com.linkmon.model.gameobject.items.ItemIds;
import com.linkmon.model.gameobject.items.RevivePotion;
import com.linkmon.model.gameobject.items.StatFood;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.model.gameobject.linkmon.Linkmon;
import com.linkmon.model.gameobject.linkmon.StatType;
import com.linkmon.model.gameobject.poop.Poop;

public class PlayerController implements ControllerListener {
	
	private Player player;
	
	private Linkmon linkmon;
	
	private EventManager eManager;
	
	public PlayerController(Player player, EventManager eManager) {
		this.player = player;
		this.linkmon = player.getLinkmon();
		this.eManager = eManager;
		this.eManager.addControllerListener(this);
		addItem(ItemFactory.getItemFromId(ItemIds.LARGE_MEAT));
		addItem(ItemFactory.getItemFromId(ItemIds.LARGE_MEAT));
		addItem(ItemFactory.getItemFromId(ItemIds.LARGE_MEAT));
		
		saveLinkmon();
		
		this.eManager.notify(new ViewEvent(ViewEvents.UPDATE_GOLD, this.getGold()));
	}
	
	public int getGold() {
		return player.getGold();
	}

	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return player.getItems();
	}
	
	private void addItem(Item newItem) {
		
		Item copy = ItemFactory.getItemFromId(newItem.getId());
		copy.add(newItem.getQuantity() - 1);
		boolean match = false;
		for(Item item : player.getItems()) {
			if(item.getId() == newItem.getId()) {
				item.add(newItem.getQuantity());
				match = true;
				break;
			}
		}
		if(!match)
			player.getItems().add(copy);
		
		Gdx.app.log("PlayerController", "Item quantity: " + copy.getQuantity());
	}
	
	private void removeItem(Item item) {
		item.remove();
		if(item.getQuantity() < 1)
			player.getItems().remove(item);
	}
	
	public void cleanPoop(Poop poop) {
		// TODO Auto-generated method stub		
		linkmon.getPoopList().remove(poop);
		eManager.notify(new ViewEvent(ViewEvents.REMOVE_POOP, linkmon.getPoopList())); // Sends to gameSprite for thought bubble
	}
	
	private void trainLinkmon(int type) {
		if(linkmon.isExhausted()) {
			eManager.notify(new MessageEvent(MessageEvents.EXAHUSTED_TRAIN_MESSAGE, "Your Linkmon is exahausted!\nLet him rest!", true));
		}
		else {
			switch(type) {
				case(StatType.HEALTH) : {
					break;
				}
				case(StatType.ATTACK) : {
					linkmon.trainAttack();
					break;
				}
				case(StatType.DEFENSE) : {
					break;
				}
				case(StatType.SPEED) : {
					break;
				}
			}
		}
	}
	
	private void useItem(Item item) {
		boolean remove = false;
		if(item.getClass() == Food.class || item.getClass() == StatFood.class) {
			remove = feedLinkmon((Food)item);
		}
		else if(item.getClass() == RevivePotion.class) {
			remove = reviveLinkmon((RevivePotion)item);
		}
		else
			remove = item.useItem(player.getWorld());
		if(remove)
			removeItem(item);
	}
	
	private boolean feedLinkmon(Food food) {
		boolean feed = food.useItem(linkmon);
		if(feed && food.getClass() == StatFood.class)
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmons stats have increased!", true));
		if(!feed)
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmon is full!", true));
		return feed;
	}
	
	private boolean reviveLinkmon(RevivePotion potion) {
		if(potion.useItem(linkmon)) {
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmon has been revived!", true));
			return true;
		}
		else {
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmon is not dead!", true));
			return false;
		}
	}
	
	private void buyItem(Item item) {
		// TODO Auto-generated method stub
		Gdx.app.log("PlayerController", "Amount: " + item.getQuantity());
		if(player.getGold() >= item.getPrice()*item.getQuantity()) {
			player.removeGold(item.getPrice()*item.getQuantity());
			
			addItem(item);
			
			eManager.notify(new ViewEvent(ViewEvents.UPDATE_GOLD, player.getGold()));
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Bought: "+item.getName()+" x "+item.getQuantity()+"!", false));
		}
		else
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Not enough gold!", false));
	}
	
	public void saveLinkmon() {
		player.createBattleLinkmon();
		player.setSavedLinkmon(player.getBattleLinkmon());
	}
	
	public void receiveGift(int itemId) {
		if(System.nanoTime() - player.getGiftTime() > 1800000000000L) { // 30mins
			player.setGiftTime(System.nanoTime());
			Item item = ItemFactory.getItemFromId(itemId);
			addItem(item);
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Mystery Gift: "+item.getName()+" x "+item.getQuantity()+"!", false));
		}
		else
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "You can't do that yet!\nSeconds left: " + ((1800000000000L-(System.nanoTime() - player.getGiftTime()))/ 1000000000), false));
	}
	
	private void receiveBattleRewards(int[] rewards) {
		linkmon.getStats().setHealth(linkmon.getStats().getHealth()+rewards[0]);
		linkmon.getStats().setAttack(linkmon.getStats().getAttack()+rewards[1]);
		linkmon.getStats().setDefense(linkmon.getStats().getDefense()+rewards[2]);
		linkmon.getStats().setSpeed(linkmon.getStats().getSpeed()+rewards[3]);
		player.addGold(rewards[4]);
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_GOLD, player.getGold()));
		
		linkmon.rankCheck();
		
		eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Got Battle Rewards!", false));
	}

	@Override
	public boolean onNotify(ControllerEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ControllerEvents.ITEM_USED) : {
				useItem(event.item);
				break;
			}
			case(ControllerEvents.CLEAN_POOP) : {
				cleanPoop(event.poop);
				break;
			}
			case(ControllerEvents.ITEM_BUY) : {
				buyItem(event.item);
				break;
			}
			case(ControllerEvents.RECIEVE_GIFT) : {
				receiveGift(event.screen);
				break;
			}
			case(ControllerEvents.WIN_LOSS): {
				receiveBattleRewards(event.values);
				break;
			}
			case(ControllerEvents.SAVE_GAME): {
				GameSave.saveGame(player);
				break;
			}
		}
		return false;
	}

	public BattleLinkmon getBattleLinkmon() {
		// TODO Auto-generated method stub
		player.createBattleLinkmon();
		return player.getBattleLinkmon();
	}

	public BattleLinkmon getSavedLinkmon() {
		// TODO Auto-generated method stub
		return player.getSavedLinkmon();
	}

	public String getName() {
		// TODO Auto-generated method stub
		return player.getName();
	}

	public void updateLoad() {
		// TODO Auto-generated method stub
		player.updateLoad();
	}

	public long getGiftTime() {
		// TODO Auto-generated method stub
		return player.getGiftTime();
	}
}
