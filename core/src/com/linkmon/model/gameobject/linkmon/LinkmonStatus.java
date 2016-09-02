package com.linkmon.model.gameobject.linkmon;

public class LinkmonStatus {
	
	private boolean isHungry;
	private int hungerLevel = 100;
	private boolean isSleepy = false; // Check phone clock
	private boolean isExhausted;
	private int exhaustionLevel = 100;
	private boolean isSick; // From not cleaning poops, making pet exhausted, staying hungry too long etc
	private int careMistakes; // From not cleaning poops, making pet exhausted, staying hungry too long etc
	private int happiness = 100; // Not sure what to do with this, just an idea
	private boolean evolveConditionMet = false;

}
