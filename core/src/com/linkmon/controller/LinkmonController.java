package com.linkmon.controller;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.model.gameobject.linkmon.BirthDate;
import com.linkmon.model.gameobject.linkmon.Linkmon;
import com.linkmon.model.gameobject.linkmon.LinkmonStats;
import com.linkmon.model.gameobject.poop.Poop;
import com.linkmon.view.GameSprite;
import com.linkmon.view.PoopSprite;

public class LinkmonController implements ControllerListener {
	
	private Linkmon linkmon;
	private EventManager eManager;
	
	private LinkmonTimersController timersController;
	
	public LinkmonController(Linkmon linkmon, EventManager eManager) {
		this.eManager = eManager;
		this.eManager.addControllerListener(this);
		this.linkmon = linkmon;
		
		//timersController = new LinkmonTimersController(linkmon, eManager);
	}
	
	public void update() {
		//timersController.update();
	}
	
	public void updateStats(int health, int attack, int defense, int speed) {
		
	}
	
	public void feed(int foodId) {
		
	}
	
	public void train(int statId) {
		switch(statId) {
		
		}
	}

	public int getHungerLevel() {
		// TODO Auto-generated method stub
		return linkmon.getHungerLevel();
	}

	public int getMove1() {
		// TODO Auto-generated method stub
		return linkmon.getMove1();
	}
	
	public int getMove2() {
		// TODO Auto-generated method stub
		return linkmon.getMove2();
	}

	public void updateLinkmonStats() {
		// TODO Auto-generated method stub
		//linkmon.updateStats();
	}

	public int getCurrentAnimation() {
		// TODO Auto-generated method stub
		return linkmon.getCurrentAnimation();
	}

	public void setAnimiationType(int currentAnimation) {
		// TODO Auto-generated method stub
		linkmon.setCurrentAnimation(currentAnimation);
	}

	public int getDirection() {
		// TODO Auto-generated method stub
		return linkmon.getDirection();
	}

	public boolean getIsMoving() {
		// TODO Auto-generated method stub
		return linkmon.isMoving();
	}

	public List<Poop> getPoopList() {
		// TODO Auto-generated method stub
		return linkmon.getPoopList();
	}

	public LinkmonStats getStats() {
		// TODO Auto-generated method stub
		return linkmon.getStats();
	}

	@Override
	public boolean onNotify(ControllerEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ControllerEvents.CLICKED_LINKMON): {
				linkmon.setAngry(true);
				break;
			}
		}
		return false;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return linkmon.getId();
	}

	public int getCareMistakes() {
		// TODO Auto-generated method stub
		return linkmon.getCareMistakes();
	}

	public BirthDate getBirthDate() {
		// TODO Auto-generated method stub
		return linkmon.getBirthDate();
	}

	public int getRank() {
		// TODO Auto-generated method stub
		return linkmon.getRank();
	}

	public void updateLoad() {
		// TODO Auto-generated method stub
		linkmon.updateLoad();
	}

	public long getElapsedPoop() {
		// TODO Auto-generated method stub
		return System.nanoTime()-linkmon.getLastPooped();
	}
}
