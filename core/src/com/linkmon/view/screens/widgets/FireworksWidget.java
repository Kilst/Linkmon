package com.linkmon.view.screens.widgets;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.view.particles.ParticleIds;

public class FireworksWidget {
	
	EventManager eManager;
	
	float delta = 0;
	long lastFrame = System.currentTimeMillis();
	
	float timer = 0;
	
	public FireworksWidget(EventManager eManager) {
		
		this.eManager = eManager;
		
	}
	
	public void update() {
		
		delta = (System.currentTimeMillis() - lastFrame);
		lastFrame = System.currentTimeMillis();
		
		timer += delta;
		
		if(timer > 100) {
			timer = 0;
			eManager.notify(new ScreenEvent(ScreenEvents.ADD_PARTICLE_EFFECT, ParticleIds.STAR, 490f, 550f));
			eManager.notify(new ScreenEvent(ScreenEvents.ADD_PARTICLE_EFFECT, ParticleIds.GREEN_STAR, 640f, 600f));
			eManager.notify(new ScreenEvent(ScreenEvents.ADD_PARTICLE_EFFECT, ParticleIds.STAR, 790f, 550f));
		}
	}

}
