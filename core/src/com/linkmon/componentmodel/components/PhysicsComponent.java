package com.linkmon.componentmodel.components;

import com.linkmon.componentmodel.gameobject.GameObject;

public class PhysicsComponent implements IPhysicsComponent {
	
	protected float moveX = 0;
	protected float moveY = 0;
	
	protected boolean move = false;

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		if(move)
			move(object);
	}

	public void setMoveTo(float x, float y) {
		moveX = x;
		moveY = y;
		
		move = true;
	}
	
	public void setMoveToX(float x) {
		moveX = x;
		
		move = true;
	}
	
	public void move(GameObject object) {
		move = false;
	}
}
