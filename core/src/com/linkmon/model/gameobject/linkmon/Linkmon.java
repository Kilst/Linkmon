package com.linkmon.model.gameobject.linkmon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.HelpMessages;
import com.linkmon.model.ModelService;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.poop.Poop;
import com.linkmon.view.screens.ScreenType;

public class Linkmon extends GameObject {
	
	private LinkmonStats stats;
	private int move1;
	private int move2;
	
	private int rank;
	private int growthStage;
	
	private boolean isHungry;
	private int hungerLevel = 100;
	private boolean isSleepy = false; // Check phone clock
	private boolean isAlseep = false;
	private boolean isExhausted;
	private int exhaustionLevel = 100;
	private boolean isSick; // From not cleaning poops, making pet exhausted, staying hungry too long etc
	private int careMistakes; // From not cleaning poops, making pet exhausted, staying hungry too long etc
	private int happiness = 100; // Not sure what to do with this, just an idea
	private boolean evolveConditionMet = false;
	private BirthDate birthDate;
	
	private List<Poop> poopList;
	private long lastPooped = 0;
	
	private LinkmonTimers timers;
    
    private boolean isDead;
    
    private int currentAnimation;
	
	private EventManager eManager;
	
	private World world;
	private boolean isAngry = false;
	
	public Linkmon() {
		super(99, 0, 45);
		timers = new LinkmonTimers();
	}
	
	public Linkmon(int id, float x, float y, EventManager eManager) {
		super(id, x, y);
		// get linkmon base stats from id
		this.eManager = eManager;
		poopList = new ArrayList<Poop>();
		
		setStats(new LinkmonStats(this, 100, 9, 7, 10));
		
		timers = new LinkmonTimers();
		
		move1 = MoveIds.HEADBUTT;
		move2 = MoveIds.KICK;
		
		birthDate = new BirthDate(this);
		
		growthStage = GrowthStages.BABY;
		
		rank = RankIds.E;
	}
	
	public void updateLoad() {
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_HUNGER_LEVEL, this.hungerLevel));
		
		for(Poop poop : poopList) {
			eManager.notify(new ViewEvent(ViewEvents.UPDATE_POOP, poop));
		}
	}
	
	public void evolve(int newId) {
		
		setId(newId);
		
		growthStage += 1;
		
		float[] statMultipliers = StatMultipliers.getMultipliers(newId);
		
		getStats().setHealth((int) (getStats().getHealth()*statMultipliers[0]));
		getStats().setAttack((int) (getStats().getAttack()*statMultipliers[0]));
		getStats().setDefense((int) (getStats().getDefense()*statMultipliers[0]));
		getStats().setSpeed((int) (getStats().getSpeed()*statMultipliers[0]));
		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.EVOLVE));
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_SPRITE));
		eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmon has evolved!!", false));
	}
	
	public void rankCheck() {
		int rank = getStats().rankCheck();
		if(this.rank == rank)
			return;
		else {
			this.rank = rank;
			eManager.notify(new MessageEvent(MessageEvents.RANK_UP, "Your Linkmons rank has increased!!", this.rank));
		}
	}
	
	@Override
	public void update() {
		super.update();
		timers.update();
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_POSITION, getX(), getY()));
	}

	public int getMove1() {
		return move1;
	}

	public void setMove1(int move1) {
		this.move1 = move1;
	}

	public int getMove2() {
		return move2;
	}

	public void setMove2(int move2) {
		this.move2 = move2;
	}

	public boolean isHungry() {
		return isHungry;
	}

	public void setHungry(boolean isHungry) {
		this.isHungry = isHungry;
	}

	public int getHungerLevel() {
		return hungerLevel;
	}

	public boolean feed(int hungerLevel, int factor) {
		if(this.hungerLevel == 100)
			return false;
		setHungerLevel(this.hungerLevel + hungerLevel);
		//timers.updateHungerTimer(factor);
		return true;
	}
	
	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
		if(this.hungerLevel < 1)
			this.hungerLevel = 0;
		else if(this.hungerLevel > 100)
			this.hungerLevel = 100;
		
		if(eManager != null)
			eManager.notify(new ViewEvent(ViewEvents.UPDATE_HUNGER_LEVEL, this.hungerLevel));
	}

	public boolean isSleepy() {
		return isSleepy;
	}

	public void setSleepy(boolean isSleepy) {
		this.isSleepy = isSleepy;
	}

	public boolean isExhausted() {
		return isExhausted;
	}

	public void setExhausted(boolean isExhausted) {
		this.isExhausted = isExhausted;
	}

	public int getExhaustionLevel() {
		return exhaustionLevel;
	}

	public void setExhaustionLevel(int exhaustionLevel) {
		this.exhaustionLevel = exhaustionLevel;
	}

	public boolean isSick() {
		return isSick;
	}

	public void setSick(boolean isSick) {
		this.isSick = isSick;
	}

	public int getCareMistakes() {
		return careMistakes;
	}

	public void addCareMistake() {
		this.careMistakes += 1;
	}

	public int getHappiness() {
		return happiness;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public boolean isEvolveConditionMet() {
		return evolveConditionMet;
	}

	public void setEvolveConditionMet(boolean evolveConditionMet) {
		this.evolveConditionMet = evolveConditionMet;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public int getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(int currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public List<Poop> getPoopList() {
		return poopList;
	}

	public void setPoopList(List<Poop> poopList) {
		this.poopList = poopList;
	}

	public LinkmonTimers getTimers() {
		return timers;
	}

	public void setTimers(LinkmonTimers timers) {
		this.timers = timers;
	}

	public LinkmonStats getStats() {
		return stats;
	}

	public void setStats(LinkmonStats stats) {
		this.stats = stats;
		stats.addLinkmon(this);
	}

	public void addedToWorld(World world) {
		// TODO Auto-generated method stub
		this.world = world;
		timers.startTimers(this, world, eManager);
	}

	public void trainAttack() {
		// TODO Auto-generated method stub
		// Get actual stat increase from linkmon growthStage
		stats.setAttack(stats.getAttack()+5);
		rankCheck();
	}
	
	@Override
	public String toString() {
		return "Fireboy " + getId();
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getGrowthStage() {
		return growthStage;
	}

	public void setGrowthStage(int growthStage) {
		this.growthStage = growthStage;
	}

	public BirthDate getBirthDate() {
		return birthDate;
	}

	public boolean isAlseep() {
		return isAlseep;
	}

	public void setAlseep(boolean isAlseep) {
		this.isAlseep = isAlseep;
	}

	public void setAngry(boolean angry) {
		// TODO Auto-generated method stub
		isAngry = angry;
	}
	
	public boolean isAngry() {
		// TODO Auto-generated method stub
		return isAngry;
	}

	public void setBirthDate(BirthDate birthDate) {
		// TODO Auto-generated method stub
		this.birthDate = birthDate;
	}

	public void seteManager(EventManager eManager) {
		// TODO Auto-generated method stub
		this.eManager = eManager;
	}

	public long getLastPooped() {
		return lastPooped;
	}

	public void setLastPooped(long lastPooped) {
		this.lastPooped = lastPooped;
	}
}
