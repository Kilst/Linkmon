package com.linkmon.model.gameobject.linkmon;

import java.util.Calendar;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.Timer;
import com.linkmon.helpers.TimerLengths;
import com.linkmon.model.ModelService;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.poop.Poop;

public class LinkmonTimers {
	
	private Timer evolutionTimer;
	
	private Timer hungerTimer;
	private Timer hungerMistakeTimer;
	private Timer poopTimer;
	private Timer poopMistakeTimer;
	private Timer sleepTimer;
	private Timer sleepMistakeTimer;
	private Timer sickTimer;
	private Timer sickMistakeTimer;
	private Timer deathTimer;
	
	private Timer oldAgeTimer;
	
	private Timer waveTimer;
	private Timer walkTimer;
	
	
	Linkmon linkmon;
	private World world;
	EventManager eManager;
	
	public LinkmonTimers() {
		setEvolutionTimer(new Timer(15, true));		
		setHungerTimer(new Timer(LinkmonTimerLengths.HUNGER_SECONDS, true));
//		if(poopTimer == null)
		setPoopTimer(new Timer(5, true));
		sleepTimer = new Timer(10, true);
		setSickTimer(new Timer(360, true));		
		setWaveTimer(new Timer(15, true));
		setWalkTimer(new Timer(10, true));
		setOldAgeTimer(new Timer(360, true));
	}
	
	public void startTimers(Linkmon linkmon, World world, EventManager eManager) {
		
		this.linkmon = linkmon;
		this.setWorld(world);
		this.eManager = eManager;
		
		getEvolutionTimer().start();
		getHungerTimer().start();
		getPoopTimer().start();
		sleepTimer.start();
		getSickTimer().start();		
		getWaveTimer().start();
		getWalkTimer().start();
		getOldAgeTimer().start();
	}
	
	public void updateHungerTimer(int factor) {
		getHungerTimer().stop();
		setHungerTimer(new Timer(2*factor, true));
		getHungerTimer().start();
	}
	
//	public void updatePoopTimer(long newStart) {
//		setPoopTimer(new Timer(LinkmonTimerLengths.POOP_SECONDS-(newStart/TimerLengths.NANO_FROM_SECOND), LinkmonTimerLengths.POOP_SECONDS, true));
//	}
	
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
		if(getOldAgeTimer().checkTimer()) {
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
		if(getEvolutionTimer().checkTimer()) {
			int newId = EvolutionChecks.evolutionCheck(linkmon);
			if(linkmon.getId() != newId) {
				linkmon.evolve(newId);
			}
		}
	}

	private void sleepCheck() {
		// Sleepy Check
		if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 7 || Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 6) {
			if(!linkmon.isSleepy() && getWorld().isLightOn()) {
				linkmon.setSleepy(true);
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_SLEEPY, true));
				setSleepMistakeTimer(new Timer(3600, true));
				getSleepMistakeTimer().start();
				eManager.notify(new ViewEvent(ViewEvents.LINKMON_ALSEEP, false));
			}
			else if(linkmon.isSleepy() && !getWorld().isLightOn()) {
				linkmon.setAlseep(true);
				playSleepAnimation();
				linkmon.setSleepy(false);
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_SLEEPY, false));
				eManager.notify(new ViewEvent(ViewEvents.LINKMON_ALSEEP, true));
				getSleepMistakeTimer().stop();
			}
			else if(linkmon.isAlseep() && getWorld().isLightOn()){
				linkmon.setAngry(true);
				linkmon.setSleepy(true);
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_SLEEPY, true));
				linkmon.setAlseep(false);
				eManager.notify(new ViewEvent(ViewEvents.LINKMON_ALSEEP, false));
			}
				
				
			if(getSleepMistakeTimer().checkTimer() && getWorld().isLightOn()) {
				linkmon.addCareMistake();
				getSleepMistakeTimer().stop();
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
		if(getSickTimer().checkTimer() && linkmon.getPoopList().size() == 3) { // Only check if there are 3 poops
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
		if(getPoopTimer().checkTimer() && ((System.nanoTime()-linkmon.getLastPooped()) > LinkmonTimerLengths.POOP_SECONDS*TimerLengths.NANO_FROM_SECOND)) {
			if(linkmon.getPoopList().size() < 3) {
				Poop poop = new Poop(0,linkmon.getX(), linkmon.getY());
				linkmon.getPoopList().add(poop);
				linkmon.setLastPooped(System.nanoTime());
//				Random rnd = new Random(); // Randomize next poop time a little
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_POOP, poop));
			}
			else if (getPoopMistakeTimer() == null) {
				setPoopMistakeTimer(new Timer(360, false));
				getPoopMistakeTimer().start();
			}
		}
		if(getPoopMistakeTimer() != null && getPoopMistakeTimer().checkTimer()) {
			linkmon.addCareMistake();
			setPoopMistakeTimer(null);
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "You didn't clean your Linkmon's poop!\nCurrent Mistakes: " + linkmon.getCareMistakes(), false));
		}
	}
	
	private void deathCheck() {
		// Death Timer
		if(getDeathTimer() != null && getDeathTimer().checkTimer()) {
			linkmon.setDead(true);
			eManager.notify(new ViewEvent(ViewEvents.LINKMON_DEAD, linkmon.getId()));
			eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Your Linkmon is dead! :(\n\nWhy you no feed?", false));
			getDeathTimer().stop();
		}
	}
	
	private void hungerCheck() {
		// Hunger timer
		if(getHungerTimer().checkTimer()) {
			linkmon.setHungerLevel(linkmon.getHungerLevel()-1);
			if(linkmon.getHungerLevel() == 0) {
				if(getDeathTimer() == null) {
					eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "You didn't feed your Linkmon!\nCurrent Mistakes: " + linkmon.getCareMistakes(), false));
					setDeathTimer(new Timer(8600, false)); // Sets Death Timer
					getDeathTimer().start();
				}
			}
			else if(linkmon.getHungerLevel() <= 20 && !linkmon.isHungry()) {
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_HUNGRY, true));
				linkmon.setHungry(true);
			}
			else if(linkmon.getHungerLevel() > 20 && linkmon.isHungry()) {
				linkmon.setHungry(false);
				eManager.notify(new ViewEvent(ViewEvents.UPDATE_HUNGRY, false));
				if(getDeathTimer() != null) {
					getDeathTimer().stop();
					setDeathTimer(null);
				}
			}
		}
	}
	
	private void playSleepAnimation() {
		getWalkTimer().restart(); // Reset walk timer
		getWaveTimer().restart();
		linkmon.setMoveToX(linkmon.getX());
		linkmon.setCurrentAnimation(AnimationType.SLEEP);
		Gdx.app.log("LinkmonTimers", "Sleeping!");
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
	}
	
	private void playAngryAnimation() {
		getWalkTimer().restart(); // Reset walk timer
		getWaveTimer().restart();
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
		if(getWaveTimer().checkTimer()) {
			getWalkTimer().restart(); // Reset walk timer
			linkmon.setMoveToX(linkmon.getX());
			linkmon.setCurrentAnimation(AnimationType.WAVING);
			Gdx.app.log("LinkmonTimers", "Waving!");
			eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
		}
		
		// Walk animation timer (sets object to move)
		else if((linkmon.getCurrentAnimation() != AnimationType.WAVING) && getWalkTimer().checkTimer()) {
			linkmon.setCurrentAnimation(AnimationType.WALKING);
            Random rnd = new Random();
            linkmon.setMoveToX(rnd.nextInt((int)(getWorld().getWidth()-140*getWorld().scaleX)));
            eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
		}
		
		// Idle animation check
		else if (linkmon.getCurrentAnimation() != AnimationType.IDLE && !linkmon.isMoving() && !linkmon.isAlseep()) {
			
			linkmon.setCurrentAnimation(AnimationType.IDLE);
			eManager.notify(new ViewEvent(ViewEvents.UPDATE_LINKMON_ANIMATION, linkmon.getCurrentAnimation(), linkmon.getDirection()));
		}
	}

	public Timer getOldAgeTimer() {
		return oldAgeTimer;
	}
	
	public void setOldAgeTimer(Timer oldAgeTimer) {
		this.oldAgeTimer = oldAgeTimer;
	}

	public Timer getEvolutionTimer() {
		return evolutionTimer;
	}

	public void setEvolutionTimer(Timer evolutionTimer) {
		this.evolutionTimer = evolutionTimer;
	}

	public Timer getSleepMistakeTimer() {
		return sleepMistakeTimer;
	}

	public void setSleepMistakeTimer(Timer sleepMistakeTimer) {
		this.sleepMistakeTimer = sleepMistakeTimer;
	}

	public Timer getSickTimer() {
		return sickTimer;
	}

	public void setSickTimer(Timer sickTimer) {
		this.sickTimer = sickTimer;
	}

	public Timer getPoopTimer() {
		return poopTimer;
	}

	public void setPoopTimer(Timer poopTimer) {
		this.poopTimer = poopTimer;
	}

	public Timer getPoopMistakeTimer() {
		return poopMistakeTimer;
	}

	public void setPoopMistakeTimer(Timer poopMistakeTimer) {
		this.poopMistakeTimer = poopMistakeTimer;
	}

	public Timer getDeathTimer() {
		return deathTimer;
	}

	public void setDeathTimer(Timer deathTimer) {
		this.deathTimer = deathTimer;
	}

	public Timer getHungerTimer() {
		return hungerTimer;
	}

	public void setHungerTimer(Timer hungerTimer) {
		this.hungerTimer = hungerTimer;
	}

	public Timer getWalkTimer() {
		return walkTimer;
	}

	public void setWalkTimer(Timer walkTimer) {
		this.walkTimer = walkTimer;
	}

	public Timer getWaveTimer() {
		return waveTimer;
	}

	public void setWaveTimer(Timer waveTimer) {
		this.waveTimer = waveTimer;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
