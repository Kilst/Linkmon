package com.linkmon.model.components;

import java.util.List;

import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.linkmon.poop.PoopInputComponent;

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
	
	@Override
	public void update(GameObject object, List<GameObject> objects) {
		if(collisionComponent != null)
			collisionComponent.testCollision(object, objects);
		
		if(move)
			move(object);
	}
	
	protected abstract void collisionTest(GameObject object, List<GameObject> objects);

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
