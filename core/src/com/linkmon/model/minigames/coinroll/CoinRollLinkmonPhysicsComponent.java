package com.linkmon.model.minigames.coinroll;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.components.ICollisionComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.minigames.PlayablePhysicsComponent;
import com.linkmon.view.particles.ParticleIds;

public class CoinRollLinkmonPhysicsComponent extends PlayablePhysicsComponent {
	
	private float rotation = 0;

	public CoinRollLinkmonPhysicsComponent(ICollisionComponent collisionComponent, boolean gravity, boolean friction) {
		super(collisionComponent, gravity, friction);
		// TODO Auto-generated constructor stub
		
		this.friction = 1.005f;
	}
	
	@Override
	public void update(GameObject object, List<GameObject> objects) {
		
		delta = (System.currentTimeMillis() - lastFrame)/10;		
		lastFrame = System.currentTimeMillis();
		
		applyVelocity(object);
		applyFriction();
		applyGravity(object);
		collisionTest(object, objects);
		
		calculateRotation(object);
	}
	
	@Override
	protected void collisionTest(GameObject object, List<GameObject> objects) {
		if(collisionComponent != null) {
			
			collisionComponent.testCollision(object, objects);
			
			for(GameObject collideObject : collisionComponent.getCollisionList()) {
				if(collideObject.getType() == ObjectType.FIREBALL) {
					object.getWorld().geteManager().notify(new ModelEvent(ModelEvents.MINIGAME_WIN));
				}
				else {
					object.getWorld().geteManager().notify(new ModelEvent(ModelEvents.RING_COLLECTED));
					collideObject.getWorld().removeObjectFromWorld(collideObject);
				}
			}
			
			if(object.getX() < 0) {
				object.setX(0);
				veloX = veloX*-0.9f;
				object.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.BOUNCE, 0, object.getY()+object.getHalfHeight()));
			}
			else if(object.getX() > 1280-object.getWidth()) {
				object.setX(1280-object.getWidth());
				veloX = veloX*-0.9f;
				object.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.BOUNCE, 1280, object.getY()+object.getHalfHeight()));
			}
		}
	}
	
	private void calculateRotation(GameObject object) {
		float circumference = (float) (2*Math.PI*(object.getWidth()/2)); // 2*pi*R
		
		rotation = (circumference/360)*veloX; // calculate 1 degree unit, multiply by velocity
		// eg a 60 unit circumference would rotate 360 in 1 frame if our velocity was also 60
		((LibgdxRenderingComponent)object.getRenderer()).getSprite().rotate(-rotation); // negative because it rotates counter-clockwise
	}

}
