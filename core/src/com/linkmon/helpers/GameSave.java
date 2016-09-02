package com.linkmon.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.linkmon.model.Player;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.model.gameobject.items.ItemFactory;
import com.linkmon.model.gameobject.linkmon.BirthDate;
import com.linkmon.model.gameobject.linkmon.Linkmon;
import com.linkmon.model.gameobject.linkmon.LinkmonStats;
import com.linkmon.model.gameobject.linkmon.LinkmonTimerLengths;
import com.linkmon.model.gameobject.poop.Poop;
import com.linkmon.view.WorldRenderer;

public class GameSave {
	
	
	public static class PlayerSave {
		
		public PlayerSave(String name, int gold) {
			this.name = name;
			this.gold = gold;
		}
		public String name;
		public int gold;
	}
	
	public static class ItemSave {
		
		public ItemSave(Item item) {
			this.id = item.getId();
			this.quantity = item.getQuantity();
		}
		public ItemSave() {
			
		}
		public int quantity;
		public int id;
	}
	
	public static class ItemListSave {
		
		public ItemListSave(List<Item> list) {
			this.itemList = new ArrayList<ItemSave>();
			for(Item item : list) {
				this.itemList.add(new ItemSave(item));
			}
		}
		public ItemListSave() {
			
		}
		public ArrayList<ItemSave> itemList;
	}
	
	public static class PoopSave {
		
		public PoopSave(Poop poop) {
			this.x = poop.getX();
		}
		public PoopSave(){
			
		}
		public float x;
	}
	
	public static class PoopListSave {
		
		public PoopListSave(List<Poop> list) {
			this.poopList = new ArrayList<PoopSave>();
			for(Poop poop : list) {
				this.poopList.add(new PoopSave(poop));
			}
		}
		public PoopListSave() {
			
		}
		public ArrayList<PoopSave> poopList;
	}
	
	public static class LinkmonSave {
		
		public LinkmonSave(int move1, int move2, int id, int hungerLevel, int exhaustionLevel, boolean isSick, int careMistakes, int happiness, long lastPooped) {

			this.move1 = move1;
			this.move2 = move2;
			
			this.id = id;
			
			this.hungerLevel = hungerLevel;
			this.exhaustionLevel = exhaustionLevel;
			this.isSick = isSick; // From not cleaning poops, making pet exhausted, staying hungry too long etc
			this.careMistakes = careMistakes; // From not cleaning poops, making pet exhausted, staying hungry too long etc
			this.happiness = happiness; // Not sure what to do with this, just an idea
			
			this.lastPooped = lastPooped;
			
		}
		
		// LINKMON
		public int move1;
		public int move2;
		
		public int id;
		
		public int hungerLevel;
		public int exhaustionLevel;
		public boolean isSick; // From not cleaning poops, making pet exhausted, staying hungry too long etc
		public int careMistakes; // From not cleaning poops, making pet exhausted, staying hungry too long etc
		public int happiness; // Not sure what to do with this, just an idea
		
		public long lastPooped;
	}
	
	public static class LinkmonStatsSave {
		public int health;
		public int attack;
		public int defense;
		public int speed;
		
		public LinkmonStatsSave(int health, int attack, int defense, int speed) {
			this.health = health;
			this.attack = attack;
			this.defense = defense;
			this.speed = speed;
		}
	}
	
	public static class LinkmonBirthDateSave {
		public int hour;
		public int minute;
		public int day;
		public int month;
		public int year;
		public long nano;
		
		public LinkmonBirthDateSave(int hour, int minute, int day, int month, int year, long nano) {
			this.hour = hour;
			this.minute = minute;
			this.day = day;
			this.month = month;
			this.year = year;
			this.nano = nano;
		}
	}
	
	public static class TimerSave {
		
		public long savedTime;
		
		public TimerSave() {
			savedTime = System.nanoTime();
		}
	}
	
	
	
	public static LinkmonSave saveLinkmon(Linkmon linkmon) {
		return new LinkmonSave(linkmon.getMove1(), linkmon.getMove2(), linkmon.getId(), linkmon.getHungerLevel(), linkmon.getExhaustionLevel(),
				linkmon.isSick(), linkmon.getCareMistakes(), linkmon.getHappiness(), linkmon.getLastPooped());
	}
	
	public static LinkmonStatsSave saveLinkmonStats(Linkmon linkmon) {
		return new LinkmonStatsSave(linkmon.getStats().getHealth(), linkmon.getStats().getAttack(), linkmon.getStats().getDefense(), linkmon.getStats().getSpeed());
	}
	
	public static LinkmonBirthDateSave saveLinkmonBirthDate(Linkmon linkmon) {
		return new LinkmonBirthDateSave(linkmon.getBirthDate().getHour(), linkmon.getBirthDate().getMinute(), 
				linkmon.getBirthDate().getDay(), linkmon.getBirthDate().getMinute(), linkmon.getBirthDate().getYear(),
				linkmon.getBirthDate().getNano());
	}
	
	public static PlayerSave savePlayer(Player player) {
		return new PlayerSave(player.getName(), player.getGold());
	}
	
	public static void saveGame(Player player) {
		String jsonText = new String();
		Json json = new Json();
		
		jsonText = json.toJson(savePlayer(player));
//		Gdx.app.log("SAVE GAME", jsonText);
		
		
		FileHandle file = Gdx.files.local("playerSave.json");
		file.writeString(jsonText, false); 

		jsonText = json.toJson(new ItemListSave(player.getItems()));
		
//		Gdx.app.log("SAVE GAME", jsonText);
		
		file = Gdx.files.local("itemSave.json");
		file.writeString(jsonText, false);
		
		Linkmon linkmon = player.getLinkmon();
		
		jsonText = json.toJson(saveLinkmon(linkmon));
		
//		Gdx.app.log("SAVE GAME", jsonText);
		
		file = Gdx.files.local("linkmonSave.json");
		file.writeString(jsonText, false);
		
		
		jsonText = json.toJson(saveLinkmonStats(linkmon));
		
//		Gdx.app.log("SAVE GAME", jsonText);
		
		file = Gdx.files.local("linkmonStatsSave.json");
		file.writeString(jsonText, false);
		
		
		jsonText = json.toJson(saveLinkmonBirthDate(linkmon));
		
//		Gdx.app.log("SAVE GAME", jsonText);
		
		file = Gdx.files.local("linkmonBirthDateSave.json");
		file.writeString(jsonText, false);
		
		jsonText = json.toJson(new PoopListSave(linkmon.getPoopList()));
		
//		Gdx.app.log("SAVE GAME", jsonText);
		
		file = Gdx.files.local("poopSave.json");
		file.writeString(jsonText, false);
		
		jsonText = json.toJson(new TimerSave());
		
//		Gdx.app.log("SAVE GAME", jsonText);
		
		file = Gdx.files.local("timerSave.json");
		file.writeString(jsonText, false);
	}
	
	public static Player loadPlayerSave() {
		Json json = new Json();
		Player player = json.fromJson(Player.class, Gdx.files.local("playerSave.json"));
		return player;
	}
	
	public static ArrayList<Item> loadPlayerItemsSave() {
		Json json = new Json();
		ArrayList<Item> itemsList = new ArrayList<Item>();
		ItemListSave itemJson = json.fromJson(ItemListSave.class, Gdx.files.local("itemSave.json"));
		for (ItemSave item : itemJson.itemList) {
			Item itemA = ItemFactory.getItemFromId(item.id);
			itemA.add(item.quantity);
			itemsList.add(itemA);
		}
		return itemsList;
	}
	
	public static Linkmon loadLinkmonSave() {
		Json json = new Json();
		Linkmon linkmon = json.fromJson(Linkmon.class, Gdx.files.local("linkmonSave.json"));
		return linkmon;
	}
	
	public static LinkmonStats loadLinkmonStatsSave() {
		Json json = new Json();
		LinkmonStats stats = json.fromJson(LinkmonStats.class, Gdx.files.local("linkmonStatsSave.json"));
		return stats;
	}
	
	public static BirthDate loadLinkmonBirthDateSave() {
		Json json = new Json();
		BirthDate dob = json.fromJson(BirthDate.class, Gdx.files.local("linkmonBirthDateSave.json"));
		return dob;
	}
	
	private static void loadPoopSave(Linkmon linkmon) {
		Json json = new Json();
		ArrayList<Poop> poopList = new ArrayList<Poop>();
		PoopListSave poopJson = json.fromJson(PoopListSave.class, Gdx.files.local("poopSave.json"));
		for (PoopSave poop : poopJson.poopList) {
			poopList.add(new Poop(0, poop.x, 40*WorldRenderer.scaleY));
		}
		
		Gdx.app.log("GameSave", "poops: " + poopList.size());
		TimerSave timerJson = json.fromJson(TimerSave.class, Gdx.files.local("timerSave.json"));
		int count = (int)Math.floor(((System.nanoTime()-linkmon.getLastPooped())/(LinkmonTimerLengths.POOP_SECONDS*TimerLengths.NANO_FROM_SECOND)));
		Gdx.app.log("GameSave", "save update poops: " + count);
		if(count > 3)
			count = 3;
		
		if(count != 0)
			linkmon.setLastPooped(System.nanoTime()-((count*TimerLengths.NANO_FROM_SECOND) - (System.nanoTime()-linkmon.getLastPooped())));
		
		Random random = new Random();
		if(poopList.size() < 3 && count > 0) {
			for (int i = poopList.size(); i < count; i++) {
				Gdx.app.log("GameSave", "added poop");
				poopList.add(new Poop(0,random.nextInt((int)(Gdx.graphics.getWidth()-40*WorldRenderer.scaleX)), 40*WorldRenderer.scaleY));
			}
		}

		linkmon.setPoopList(poopList);
		
		//linkmon.getTimers().updatePoopTimer(System.nanoTime()-linkmon.getLastPooped());
	}
	
	private static void loadHungerSave(Linkmon linkmon) {
		Json json = new Json();
		
		TimerSave timerJson = json.fromJson(TimerSave.class, Gdx.files.local("timerSave.json"));
		long elapsedTime = System.nanoTime() - timerJson.savedTime;
		Gdx.app.log("GameSave", "elapsedTime: " + elapsedTime);
		int count = (int) Math.floor((elapsedTime/(TimerLengths.NANO_FROM_SECOND*LinkmonTimerLengths.HUNGER_SECONDS)));
		
		for (int i = 0; i < count; i++) {
			linkmon.setHungerLevel(linkmon.getHungerLevel()-1);
		}
	}
	
	public static void updateLinkmon(Linkmon linkmon) {
		
		loadPoopSave(linkmon);
		
		loadHungerSave(linkmon);
	}


}
