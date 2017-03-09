package com.linkmon.model.linkmon;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.components.PhysicsComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;

public class LinkmonPhysicsComponent extends PhysicsComponent {
	
	private GameObject linkmon;
	private LinkmonTimerComponent timers;
	
	public LinkmonPhysicsComponent() {
		moveY = 65;
	}
	
	@Override
	public void update(GameObject object, List<GameObject> objects) {
		// TODO Auto-generated method stub
		super.update(object, objects);
		if(linkmon == null) {
			linkmon = object;
			timers = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getTimers();
		}
		
		if(timers.getWalkTimer().checkTimer()){
			Random rnd = new Random();
			moveX = rnd.nextInt((int)(linkmon.getWorld().getWidth()-(linkmon.getWidth())));
			Gdx.app.log("LinkmonPhyicsComponent", "getWalkTimer timer ended");
			move = true;
		}
	}
	
	@Override
	public void move(GameObject object) {
		// Move X
		if(object.getX() > moveX) {
			object.setX(object.getX() - 1);
			object.isMoving = true;
			object.direction = Direction.LEFT;
		}
		else if(object.getX() < moveX) {
			object.setX(object.getX() + 1);
			object.isMoving = true;
			object.direction = Direction.RIGHT;
		}
		
		// Move Y
		if(object.getY() > moveY) {
			object.setY(object.getY() - 1);
			object.isMoving = true;
		}
		else if(object.getY() < moveY) {
			object.setY(object.getY() + 1);
			object.isMoving = true;
		}
		if(moveY == object.getY() && moveX == object.getX()) {
			move = false;
			object.isMoving = false;
		}
	}

	@Override
	protected void collisionTest(GameObject object, List<GameObject> objects) {
		// TODO Auto-generated method stub
		
	}
}
