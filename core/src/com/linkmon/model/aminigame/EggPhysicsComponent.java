package com.linkmon.model.aminigame;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.components.PhysicsComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.linkmon.poop.PoopInputComponent;

public class EggPhysicsComponent extends PhysicsComponent {
	
	private final float gravity = 0.3f;
	
	private float veloX = 0;
	private float veloY = 0;
	
	private float maxVeloX = 5;
	private float maxVeloY = -5;
	
	private float delta = 0;
	private long lastFrame = System.currentTimeMillis();
	
	private CollisionComponent collisionComponent;
	
	public EggPhysicsComponent(CollisionComponent collisionComponent) {
		// TODO Auto-generated constructor stub
		this.collisionComponent = collisionComponent;
	}

	@Override
	public void update(GameObject object, List<GameObject> objects) {
		
		delta = (System.currentTimeMillis() - lastFrame)/10;		
		lastFrame = System.currentTimeMillis();
		
		if(object.getY() > 50) {
			applyGravity();
			object.setY(object.getY()+(veloY*delta));
		}
		else {
			veloY= veloY*-1;
			object.setY(object.getY()+(veloY*delta));
		}
		
		if(collisionComponent != null) {
			collisionComponent.testCollision(object, objects);
			for(GameObject collideObject : collisionComponent.getCollisionList()) {
				if(collideObject.getType() == ObjectType.LINKMON) {
					// Setting clicked removes the poop completely next frame when PoopComonent updates.
					Gdx.app.log("EggPhysics", "Hit Linkmon!");
				}
			}
		}
	}
	
	private void applyGravity() {
		if(veloY > maxVeloY)
		veloY -= gravity;
	}

}
