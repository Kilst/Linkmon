package com.linkmon.model.minigames.coinroll;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.components.ICollisionComponent;
import com.linkmon.model.components.PhysicsComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.linkmon.poop.PoopInputComponent;

public class CoinRollItemPhysicsComponent extends PhysicsComponent {
	
	private final float gravity = 0.3f;
	
	private float veloX = 0;
	private float veloY = 0;
	
	private float maxVeloX = 5;
	private float maxVeloY = -5;
	
	private float delta = 0;
	private long lastFrame = System.currentTimeMillis();
	
	private ICollisionComponent collisionComponent;
	
	private boolean started = false;
	
	public CoinRollItemPhysicsComponent(ICollisionComponent collisionComponent) {
		// TODO Auto-generated constructor stub
		this.collisionComponent = collisionComponent;
	}

	@Override
	public void update(GameObject object, List<GameObject> objects) {
		
		if(!started) {
			lastFrame = System.currentTimeMillis();
			started = true;
		}
		
		delta = (System.currentTimeMillis() - lastFrame)/10;		
		lastFrame = System.currentTimeMillis();
		
		applyGravity();
		object.setY(object.getY()+(veloY*delta));
		
//		collisionTest(object, objects);
		
		if(object.getY()+object.getHeight() < -150)
			object.getWorld().removeObjectFromWorld(object);
	}
	
	private void applyGravity() {
		if(veloY > maxVeloY)
		veloY -= gravity;
	}

	@Override
	protected void collisionTest(GameObject object, List<GameObject> objects) {
		// TODO Auto-generated method stub
//		if(collisionComponent != null) {
//			collisionComponent.testCollision(object, objects);
//			for(GameObject collideObject : collisionComponent.getCollisionList()) {
//				if(collideObject.getType() == ObjectType.LINKMON) {
//					Gdx.app.log("CoinRollItemPhysics", "Hit Linkmon!");
//				}
//			}
//		}
	}

	public void reset() {
		// TODO Auto-generated method stub
		started = false;
		veloY = 0;
	}

}
