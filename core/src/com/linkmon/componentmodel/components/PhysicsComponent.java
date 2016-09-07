package com.linkmon.componentmodel.components;

import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;

public abstract class PhysicsComponent implements IPhysicsComponent {
	
	protected float moveX = 0;
	protected float moveY = 0;
	
	protected boolean move = false;
	
	protected CollisionComponent collisionComponent;
	
	public PhysicsComponent() {
		
	}
	
	public PhysicsComponent(CollisionComponent collisionComponent) {
		this.collisionComponent = collisionComponent;
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
