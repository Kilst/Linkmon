package com.linkmon.model.minigames.duckracing;

import java.util.List;

import com.linkmon.model.components.IPhysicsComponent;
import com.linkmon.model.gameobject.GameObject;

public class WavePhysicsComponent implements IPhysicsComponent {
	
	private static final double PERIOD1 = 256; // loop every 8 calls to updateNumber
	private static final double PERIOD2 = 128; // loop every 8 calls to updateNumber
	private double SCALE = 60; // go between 0 and 800

	private int pos1 = 0;
	private int pos2 = 0;
	private float sineNumSideway = 0;
	private float sineNumUp = 0;
	
	private float originX;
	private float originY;

	public void updateSidewaysNumber() {
	    pos1++;
	    sineNumSideway = (float)(Math.sin(pos1*2*Math.PI/PERIOD1)*(SCALE/2)+(SCALE/2));
	}
	
	public void updateUpwardsNumber() {
		pos2++;
		sineNumUp = (float)(Math.sin(pos2*2*Math.PI/PERIOD2)*(SCALE/8)+(SCALE/8));
	}

	@Override
	public void update(GameObject object, List<GameObject> objects) {
		// TODO Auto-generated method stub
		updateSidewaysNumber();
		updateUpwardsNumber();
		object.setPosition(originX + sineNumSideway, originY + sineNumUp);
	}
	
	public void setOrigin(float x, float y) {
		originX = x;
		originY = y;
		pos1 = (int) (Math.random()*100);
		pos2 = (int) (Math.random()*100);
	}

	@Override
	public void move(GameObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMoveTo(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMoveToX(float x) {
		// TODO Auto-generated method stub
		
	}

}
