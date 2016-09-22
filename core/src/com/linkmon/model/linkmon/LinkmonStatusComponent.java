package com.linkmon.model.linkmon;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.Timer;
import com.linkmon.model.components.IExtraComponents;
import com.linkmon.model.gameobject.GameObject;

public class LinkmonStatusComponent implements IExtraComponents {
	
	private boolean isDead;
	
	private BirthDate birthDate;
	private boolean isHungry;
	private int hungerLevel = 100;
	private boolean isSleepy = false; // Check phone clock
	private boolean isExhausted;
	private int exhaustionLevel = 100;
	private boolean isSick; // From not cleaning poops, making pet exhausted, staying hungry too long etc
	private int careMistakes; // From not cleaning poops, making pet exhausted, staying hungry too long etc
	private int happiness = 100; // Not sure what to do with this, just an idea
	
	private int growthStage = 0;

	private GameObject linkmon;
	private LinkmonTimerComponent timers;
	
	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		if(linkmon == null) {
			linkmon = object;
			timers = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getTimers();
			
			linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.UPDATE_EXHAUSTION_LEVEL, exhaustionLevel));
			linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.UPDATE_HUNGER_LEVEL, hungerLevel));
		}
		
		updateHunger(timers.getHungerTimer());
		
		updateExhaustion(timers.getExhaustionTimer());
	}
	
	private void updateExhaustion(Timer exhaustionTimer) {
		// TODO Auto-generated method stub
		if(exhaustionTimer.checkTimer()) {
			addExhaustionLevel(1);
		}
	}
	
	public void addExhaustionLevel(int amount) {
		// TODO Auto-generated method stub
		exhaustionLevel += amount;
		
		if(exhaustionLevel <= 11) {
			
			isExhausted = true;

			if(exhaustionLevel < 0)
				exhaustionLevel = 0;
		}
		else if(exhaustionLevel > 100)
			exhaustionLevel = 100;
		else
			isExhausted = false;
		
		linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.UPDATE_EXHAUSTION_LEVEL, exhaustionLevel));
	}

	public boolean isHungry() {
		return isHungry;
	}
	
	public boolean isSleepy() {
		return isSleepy;
	}
	
	public boolean isSick() {
		return isSick;
	}
	
	public int getCareMistakes() {
		return careMistakes;
	}
	
	private void updateHunger(Timer hungerTimer) {
		if(hungerTimer.checkTimer()) {
			Gdx.app.log("LinkmonStatusComponent", "Hunger timer ended");
			addHungerLevel(-1);
		}
	}
	
	private void updateSleepy() {
		
	}

	public void addHungerLevel(int amount) {
		// TODO Auto-generated method stub
		hungerLevel += amount;
		
		if(hungerLevel < 30) {
			
			isHungry = true;

			if(hungerLevel < 0)
				hungerLevel = 0;
		}
		else if(hungerLevel > 100)
			hungerLevel = 100;
		else
			isHungry = false;
		
		linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.UPDATE_HUNGER_LEVEL, hungerLevel));
	}

	public int getHungerLevel() {
		// TODO Auto-generated method stub
		return hungerLevel;
	}

	public BirthDate getBirthDate() {
		// TODO Auto-generated method stub
		return birthDate;
	}

	public void setBirthDate() {
		this.birthDate = new BirthDate();
	}
	
	public void setBirthDate(BirthDate birthDate) {
		this.birthDate = birthDate;
	}

	public int getGrowthStage() {
		// TODO Auto-generated method stub
		return growthStage;
	}

	public void updateGrowthStage() {
		// TODO Auto-generated method stub
		growthStage += 1;
	}

	public void setHungerLevel(int hungerLevel) {
		// TODO Auto-generated method stub
		this.hungerLevel = hungerLevel;
	}

	public void setSick(boolean isSick) {
		// TODO Auto-generated method stub
		this.isSick = isSick;
	}

	public void setCareMistakes(int careMistakes) {
		// TODO Auto-generated method stub
		this.careMistakes = careMistakes;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
}
