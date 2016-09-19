package com.linkmon.componentmodel.linkmon;

import com.linkmon.componentmodel.components.ITimerComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.helpers.Timer;
import com.linkmon.model.gameobject.linkmon.LinkmonTimerLengths;

public class LinkmonTimerComponent implements ITimerComponent {
	
	private Timer evolutionTimer;
	
	private Timer hungerTimer;
	private Timer hungerMistakeTimer;
	private Timer poopTimer;
	private Timer poopMistakeTimer;
	private Timer sleepTimer;
	private Timer sleepMistakeTimer;
	private Timer sickTimer;
	private Timer sickMistakeTimer;
	private Timer exhaustionTimer;
	
	private Timer deathTimer;
	
	private Timer oldAgeTimer;
	
	private Timer waveTimer;
	private Timer walkTimer;
	
	private boolean isRunning = false;
	
	public LinkmonTimerComponent() {
		setExhaustionTimer(new Timer(24*36, true));
		setEvolutionTimer(new Timer(15, true));		
		setHungerTimer(new Timer(1, true));
		setPoopTimer(new Timer(5, true));
		setSleepTimer(new Timer(10, true));
		setSickTimer(new Timer(360, true));		
		setWaveTimer(new Timer(15, true));
		setWalkTimer(new Timer(30, true));
		setOldAgeTimer(new Timer(360, true));
		
		startAll();
	}
	
	public void startAll() {
		getEvolutionTimer().start();
		getHungerTimer().start();
		getPoopTimer().start();
		sleepTimer.start();
		getSickTimer().start();
		getWaveTimer().start();
		getWalkTimer().start();
		getOldAgeTimer().start();
		
		isRunning = true;
	}
	
	public void stopAll() {
		getEvolutionTimer().stop();
		getHungerTimer().stop();
		getPoopTimer().stop();
		sleepTimer.stop();
		getSickTimer().stop();		
		getWaveTimer().stop();
		getWalkTimer().stop();
		getOldAgeTimer().stop();
		
		isRunning = false;
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateTimers(int growthStage) {
		switch(growthStage) {
			case(GrowthStages.BABY) : {
				setBabyTimers();
			}
			case(GrowthStages.ROOKIE) : {
				setRookieTimers();
			}
		}
	}
	
	private void setBabyTimers() {
		setHungerTimer(new Timer(1, true));
		hungerTimer.start();
		setPoopTimer(new Timer(30, true));
		poopTimer.start();
	}
	
	private void setRookieTimers() {
		setHungerTimer(new Timer(1, true));
		hungerTimer.start();
		setPoopTimer(new Timer(60, true));
		poopTimer.start();
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public Timer getEvolutionTimer() {
		return evolutionTimer;
	}

	public void setEvolutionTimer(Timer evolutionTimer) {
		this.evolutionTimer = evolutionTimer;
	}

	public Timer getHungerTimer() {
		return hungerTimer;
	}

	public void setHungerTimer(Timer hungerTimer) {
		this.hungerTimer = hungerTimer;
	}

	public Timer getHungerMistakeTimer() {
		return hungerMistakeTimer;
	}

	public void setHungerMistakeTimer(Timer hungerMistakeTimer) {
		this.hungerMistakeTimer = hungerMistakeTimer;
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

	public Timer getSleepTimer() {
		return sleepTimer;
	}

	public void setSleepTimer(Timer sleepTimer) {
		this.sleepTimer = sleepTimer;
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

	public Timer getSickMistakeTimer() {
		return sickMistakeTimer;
	}

	public void setSickMistakeTimer(Timer sickMistakeTimer) {
		this.sickMistakeTimer = sickMistakeTimer;
	}

	public Timer getDeathTimer() {
		return deathTimer;
	}

	public void setDeathTimer(Timer deathTimer) {
		this.deathTimer = deathTimer;
	}

	public Timer getOldAgeTimer() {
		return oldAgeTimer;
	}

	public void setOldAgeTimer(Timer oldAgeTimer) {
		this.oldAgeTimer = oldAgeTimer;
	}

	public Timer getWaveTimer() {
		return waveTimer;
	}

	public void setWaveTimer(Timer waveTimer) {
		this.waveTimer = waveTimer;
	}

	public Timer getWalkTimer() {
		return walkTimer;
	}

	public void setWalkTimer(Timer walkTimer) {
		this.walkTimer = walkTimer;
	}

	public Timer getExhaustionTimer() {
		// TODO Auto-generated method stub
		return exhaustionTimer;
	}

	public void setExhaustionTimer(Timer exhaustionTimer) {
		this.exhaustionTimer = exhaustionTimer;
	}

}
