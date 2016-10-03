package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.input.InputEvent;
import com.linkmon.eventmanager.input.InputListener;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.view.particles.ParticleIds;
import com.linkmon.view.particles.ParticleLoader;

public class LibgdxParticleController implements ScreenListener, ModelListener, InputListener {
	
	private ParticleLoader pLoaderWorld;
	
	private ParticleLoader pLoaderUI;
	
	private EventManager eManager;
	
	public LibgdxParticleController(EventManager eManager) {
		pLoaderWorld = new ParticleLoader();
		pLoaderUI = new ParticleLoader();
		
		eManager.addModelListener(this);
		eManager.addInputListener(this);
	}
	
	private void addParticleToWorld(int particleId, float x, float y) {
		pLoaderWorld.addParticle(particleId, x, y);
	}
	
	private void addParticleToUI(int particleId, float x, float y) {
		pLoaderUI.addParticle(particleId, x, y);
	}
	
	public ParticleLoader getWorldLoader() {
		return pLoaderWorld;
	}
	
	public ParticleLoader getUILoader() {
		return pLoaderUI;
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ModelEvents.ADD_PARTICLE_EFFECT): {
				addParticleToWorld(event.value, event.x, event.y);
				break;
			}
		}
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.ADD_PARTICLE_EFFECT): {
				addParticleToUI(event.value, event.x, event.y);
				break;
			}
		}
		return false;
	}

	@Override
	public boolean onNotify(InputEvent event) {
		// TODO Auto-generated method stub
		//addParticleToWorld(ParticleIds.GREEN_ORANGE_STAR, event.position.x, event.position.y);
		addParticleToWorld(ParticleIds.STAR, event.position.x, event.position.y);
		return false;
	}
}
