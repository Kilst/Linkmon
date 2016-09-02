package com.linkmon.controller;

import java.util.Calendar;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.Timer;
import com.linkmon.model.gameobject.linkmon.AnimationType;
import com.linkmon.model.gameobject.linkmon.EvolutionChecks;
import com.linkmon.model.gameobject.linkmon.Linkmon;
import com.linkmon.model.gameobject.linkmon.LinkmonTimers;
import com.linkmon.model.gameobject.poop.Poop;

public class LinkmonTimersController {
	
	private Linkmon linkmon;
	private EventManager eManager;
	
	private LinkmonTimers timers;
	
	public LinkmonTimersController(Linkmon linkmon, EventManager eManager) {
		this.linkmon= linkmon;
		this.eManager = eManager;
		
		timers = linkmon.getTimers();
	}
	
	public void updateHungerTimer(int factor) {
		timers.getHungerTimer().stop();
		timers.setHungerTimer(new Timer(2*factor, true));
		timers.getHungerTimer().start();
	}
	
	public void update() {
		if(linkmon.isDead())
			return;
		
		sleepCheck();
		
		if(!linkmon.isAlseep()) {
			animationCheck();
			evolutionCheck();
			sickCheck();
			poopCheck();
			deathCheck();
			hungerCheck();
			oldAgeCheck();
		}
	}
	
	private void oldAgeCheck() {
		if(timers.getOldAgeTimer().checkTimer()) {
			if(System.nanoTime() - (linkmon.getBirthDate().getNano()) > (86400000000000L*90 + ((86400000000000L*30)/(linkmon.getCareMistakes()+1)))) {
				// 90 days, + 30 days/careMistakes
				linkmon.setDead(true);
				eManager.notify(new ViewEvent(ViewEvents.LINKMON_DEAD, linkmon.getId()));
				eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmon has died of old age. :(", false));
			}
		}
	}
	
	private void evolutionCheck() {
		// TODO Auto-generated method stub
		if(timers.getEvolutionTimer().checkTimer()) {
			int newId = EvolutionChecks.evolutionCheck(linkmon);
			if(linkmon.getId() != newId) {
				linkmon.evolve(newId);
			}
		}
	}

	private void sleepCheck() {
		// Sleepy Check
		if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 7 || Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 6) {
			if(!linkmon.isSleepy() && timers.getWorld().isLightOn()) {
				linkmon.setSleepy(true);
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_SLEEPY, true));
				timers.setSleepMistakeTimer(new Timer(3600, true));
				timers.getSleepMistakeTimer().start();
				eManager.notify(new ViewEvent(ViewEvents.LINKMON_ALSEEP, false));
			}
			else if(linkmon.isSleepy() && !timers.getWorld().isLightOn()) {
				linkmon.setAlseep(true);
				playSleepAnimation();
				linkmon.setSleepy(false);
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_SLEEPY, false));
				eManager.notify(new ViewEvent(ViewEvents.LINKMON_ALSEEP, true));
				timers.getSleepMistakeTimer().stop();
			}
			else if(linkmon.isAlseep() && timers.getWorld().isLightOn()){
				linkmon.setAngry(true);
				linkmon.setSleepy(true);
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_SLEEPY, true));
				linkmon.setAlseep(false);
				eManager.notify(new ViewEvent(ViewEvents.LINKMON_ALSEEP, false));
			}
				
				
			if(timers.getSleepMistakeTimer().checkTimer() && timers.getWorld().isLightOn()) {
				linkmon.addCareMistake();
				timers.getSleepMistakeTimer().stop();
				eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "You didn't turn the lights off!\nCurrent Mistakes: " + linkmon.getCareMistakes(), false));
			}
		}
		else {
			linkmon.setAlseep(false);
			eManager.notify(new ViewEvent(ViewEvents.LINKMON_ALSEEP, false));
		}
	}
	
	private void sickCheck() {
		// Sick Check
		if(timers.getSickTimer().checkTimer() && linkmon.getPoopList().size() == 3) { // Only check if there are 3 poops
			if(!linkmon.isSick()) {
				Random rnd = new Random();
	            if(rnd.nextInt(50) == 49) { 
	            	linkmon.setSick(true);
	            	eManager.notify(new ViewEvent(ViewEvents.UPDATE_SICK, true));
	            	eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmon is sick!", false));
	            }
			}
		}
	}
	
	private void poopCheck() {
		// Poop Check
		if(timers.getPoopTimer().checkTimer()) {
			if(linkmon.getPoopList().size() < 3) {
				Poop poop = new Poop(0,linkmon.getX(), linkmon.getY());
				linkmon.getPoopList().add(poop);
//				Random rnd = new Random(); // Randomize next poop time a little
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_POOP, poop));
			}
			else if (timers.getPoopMistakeTimer() == null) {
				timers.setPoopMistakeTimer(new Timer(360, false));
				timers.getPoopMistakeTimer().start();
			}
		}
		if(timers.getPoopMistakeTimer() != null && timers.getPoopMistakeTimer().checkTimer()) {
			linkmon.addCareMistake();
			timers.setPoopMistakeTimer(null);
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "You didn't clean your Linkmon's poop!\nCurrent Mistakes: " + linkmon.getCareMistakes(), false));
		}
	}
	
	private void deathCheck() {
		// Death Timer
		if(timers.getDeathTimer() != null && timers.getDeathTimer().checkTimer()) {
			linkmon.setDead(true);
			eManager.notify(new ViewEvent(ViewEvents.LINKMON_DEAD, linkmon.getId()));
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmon is dead! :(\n\nWhy you no feed?", false));
			timers.getDeathTimer().stop();
		}
	}
	
	private void hungerCheck() {
		// Hunger timer
		if(timers.getHungerTimer().checkTimer()) {
			linkmon.setHungerLevel(linkmon.getHungerLevel()-1);
			if(linkmon.getHungerLevel() == 0) {
				if(timers.getDeathTimer() == null) {
					eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "You didn't feed your Linkmon!\nCurrent Mistakes: " + linkmon.getCareMistakes(), false));
					timers.setDeathTimer(new Timer(8600, false)); // Sets Death Timer
					timers.getDeathTimer().start();
				}
			}
			else if(linkmon.getHungerLevel() <= 20 && !linkmon.isHungry()) {
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_HUNGRY, true));
				linkmon.setHungry(true);
			}
			else if(linkmon.getHungerLevel() > 20 && linkmon.isHungry()) {
				linkmon.setHungry(false);
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_HUNGRY, false));
				if(timers.getDeathTimer() != null) {
					timers.getDeathTimer().stop();
					timers.setDeathTimer(null);
				}
			}
		}
	}
	
	private void playSleepAnimation() {
		timers.getWalkTimer().restart(); // Reset walk timer
		timers.getWaveTimer().restart();
		linkmon.setMoveToX(linkmon.getX());
		linkmon.setCurrentAnimation(AnimationType.SLEEP);
		Gdx.app.log("LinkmonTimers", "Sleeping!");
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
	}
	
	private void playAngryAnimation() {
		timers.getWalkTimer().restart(); // Reset walk timer
		timers.getWaveTimer().restart();
		linkmon.setMoveToX(linkmon.getX());
		linkmon.setCurrentAnimation(AnimationType.ANGRY);
		Gdx.app.log("LinkmonTimers", "Angry!");
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
	}
	
	private void animationCheck() {
		
//		if(linkmon.isSleepy() && !world.isLightOn()) {
//			walkTimer.restart(); // Reset walk timer
//			waveTimer.restart();
//			linkmon.setMoveToX(linkmon.getX());
//			linkmon.setCurrentAnimation(AnimationType.SLEEP);
//			Gdx.app.log("LinkmonTimers", "Sleeping!");
//			eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
//			return;
//		}
		
		// Wave animation timer
		if(linkmon.isAngry()) {
			playAngryAnimation();
			linkmon.setAngry(false);
			return;
		}
		if(timers.getWaveTimer().checkTimer()) {
			timers.getWalkTimer().restart(); // Reset walk timer
			linkmon.setMoveToX(linkmon.getX());
			linkmon.setCurrentAnimation(AnimationType.WAVING);
			Gdx.app.log("LinkmonTimers", "Waving!");
			eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
		}
		
		// Walk animation timer (sets object to move)
		else if((linkmon.getCurrentAnimation() != AnimationType.WAVING) && timers.getWalkTimer().checkTimer()) {
			linkmon.setCurrentAnimation(AnimationType.WALKING);
            Random rnd = new Random();
            linkmon.setMoveToX(rnd.nextInt((int)(timers.getWorld().getWidth()-140*timers.getWorld().scaleX)));
            eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
		}
		
		// Idle animation check
		else if (linkmon.getCurrentAnimation() != AnimationType.IDLE && !linkmon.isMoving() && !linkmon.isAlseep()) {
			
			linkmon.setCurrentAnimation(AnimationType.IDLE);
			eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
		}
	}

}
