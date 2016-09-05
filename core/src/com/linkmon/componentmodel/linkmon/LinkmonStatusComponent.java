package com.linkmon.componentmodel.linkmon;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.components.IExtraComponents;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.Timer;

public class LinkmonStatusComponent implements IExtraComponents {
	
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
		}
		
		updateHunger(timers.getHungerTimer());
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
			linkmon.getWorld().geteManager().notify(new ViewEvent(ViewEvents.UPDATE_HUNGER_LEVEL, hungerLevel));
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

	public int getGrowthStage() {
		// TODO Auto-generated method stub
		return growthStage;
	}

	public void updateGrowthStage() {
		// TODO Auto-generated method stub
		growthStage += 1;
	}
}
