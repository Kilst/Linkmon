package com.linkmon.model.minigames;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.components.ICollisionComponent;
import com.linkmon.model.components.PhysicsComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;

public class PlayablePhysicsComponent extends PhysicsComponent {

	protected final float gravity = 0.3f;
	protected float friction = 1.1f;
	
	boolean gravityEnabled = false;
	boolean frictionEnabled = false;
	
	protected float veloX = 0;
	protected float veloY = 0;
	
	private float maxVeloX = 5;
	private float maxVeloY = -5;
	
	protected float delta = 0;
	protected long lastFrame = System.currentTimeMillis();
	
	private boolean isGrounded = false;
	
	protected ICollisionComponent collisionComponent;
	
	public PlayablePhysicsComponent(ICollisionComponent collisionComponent, boolean gravity, boolean friction) {
		// TODO Auto-generated constructor stub
		this.collisionComponent = collisionComponent;
		
		gravityEnabled = gravity;
		frictionEnabled = friction;
	}

	@Override
	public void update(GameObject object, List<GameObject> objects) {
		
		delta = (System.currentTimeMillis() - lastFrame)/10;		
		lastFrame = System.currentTimeMillis();
		
		applyVelocity(object);
		applyFriction();
		applyGravity(object);
		collisionTest(object, objects);
	}
	
	@Override
	protected void collisionTest(GameObject object, List<GameObject> objects) {
		if(collisionComponent != null) {
			collisionComponent.testCollision(object, objects);
			for(GameObject collideObject : collisionComponent.getCollisionList()) {
				if(collideObject.getType() == ObjectType.POOP) {
					
				}
			}
		}
	}
	
	protected void applyVelocity(GameObject object) {
		object.setX(object.getX()+(veloX*delta));
	}
	
	protected void applyGravity(GameObject object) {
		if(gravityEnabled) {
			if(object.getY() > 50) {
				if(veloY > maxVeloY)
					veloY -= gravity;
				object.setY(object.getY()+(veloY*delta));
				isGrounded = false;
			}
			else if(object.getY() < 50) {
				object.setY(50);
				isGrounded = true;
			}
		}
	}
	
	protected void applyFriction() {
		if(frictionEnabled)
			veloX /= friction;
	}

	public void addVeloX(float veloX2) {
		// TODO Auto-generated method stub
		veloX += veloX2;
	}

	public void addVeloY(float veloY2) {
		// TODO Auto-generated method stub
		veloY += veloY2;
	}

}
