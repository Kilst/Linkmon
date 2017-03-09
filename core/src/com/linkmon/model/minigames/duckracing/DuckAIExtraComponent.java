package com.linkmon.model.minigames.duckracing;

import com.linkmon.model.components.IExtraComponents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.minigames.PlayablePhysicsComponent;

public class DuckAIExtraComponent implements IExtraComponents {
	
	private float delta = 0;
	private long lastFrame = System.currentTimeMillis();
	private float timer = 0;
	
	private boolean begin = false;
	
	private int difficulty;
	
	public DuckAIExtraComponent(int difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		if(begin) {
			delta = (System.currentTimeMillis() - lastFrame)/10;		
			lastFrame = System.currentTimeMillis();
			timer+=delta;
			if(timer + (Math.random()*difficulty*1.5f) > 15f) {
				((PlayablePhysicsComponent)object.getPhysicsComponent()).addVeloX((float) (3 + (Math.random()*difficulty)));
				timer = 0;
			}
		}
	}

	public void setBegin(boolean begin) {
		this.begin = begin;
	}

}
