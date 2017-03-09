package com.linkmon.model.minigames.coinroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.linkmon.helpers.Timer;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;

public class ObjectSpawner {
	
	private World gameWorld;
	
	private Timer spawnTimer;
	
	// Pools used due to object creation frequency
	private List<GameObject> fireballPool;
	private List<GameObject> ringPool;
	
	public ObjectSpawner(World gameWorld) {
		this.gameWorld = gameWorld;
		
		spawnTimer = new Timer(1, true);
		spawnTimer.start();
		
		fireballPool = new ArrayList<GameObject>();
		
		// Pre-build some fireballs on mini-game load
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		fireballPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL));
		
		
		ringPool = new ArrayList<GameObject>();
		
		// Pre-build some rings on mini-game load
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
		ringPool.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.RING));
	}
	
	public void update() {
		if(spawnTimer.checkTimer())
			spawnObject();
	}
	
	private void spawnObject() {
		
		GameObject object = null;
		
		Random rnd = new Random();
		
		boolean newFireball = false;
		boolean newRing = false;
		
		if (Math.random() > 0.7f) {
			spawnLots();
			return;
		}
		else if (Math.random() > 0.5f) {
			// Try to use an object from the pool
			for(GameObject gameObject : fireballPool) {
				if(!gameWorld.getObjects().contains(gameObject)) {
					// Object found that is not in the world
					object = gameObject;
					((CoinRollItemPhysicsComponent)object.getPhysicsComponent()).reset(); // Reset position and velocity
					newFireball = false;
					break;
				}
				else
					newFireball = true; // Flag to add object to pool
			}
			
			if(newFireball) {
				// All the pooled objects are currently in the world
				object = ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL);
				fireballPool.add(object); // Add object to pool
			}
			else {
				fireballPool.remove(object);
				fireballPool.add(object);
			}
		}
		else {
			for(GameObject gameObject : ringPool) {
				if(!gameWorld.getObjects().contains(gameObject)) {
					object = gameObject;
					((CoinRollItemPhysicsComponent)object.getPhysicsComponent()).reset();
					newRing = false;
					break;
				}
				else
					newRing = true;
			}
			
			if(newRing) {
				object = ObjectFactory.getInstance().getObjectFromId(ObjectId.RING);
				ringPool.add(object);
			}
			else {
				ringPool.remove(object);
				ringPool.add(object);
			}
		}
		
		object.setX(rnd.nextInt((int)(gameWorld.getWidth()-object.getWidth())));
		object.setY(Gdx.graphics.getHeight());
		gameWorld.addObjectToWorld(object);
		
	}
	
	private void spawnLots() {
		
		GameObject object = null;
		Random rnd = new Random();
		int x = 0;
		int y = Gdx.graphics.getHeight();
		boolean newFireball = false;
		
		for(int i = 0; i < rnd.nextInt(11); i++) {
			for(GameObject gameObject : fireballPool) {
				if(!gameWorld.getObjects().contains(gameObject)) {
					object = gameObject;
					((CoinRollItemPhysicsComponent)object.getPhysicsComponent()).reset();
					newFireball = false;
					break;
				}
				else
					newFireball = true;
			}
			
			if(newFireball) {
				object = ObjectFactory.getInstance().getObjectFromId(ObjectId.FIREBALL);
				fireballPool.add(object);
			}
			else {
				fireballPool.remove(object);
				fireballPool.add(object);
			}
			
			object.setX(rnd.nextInt((int)(gameWorld.getWidth()-object.getWidth())));
			object.setY(Gdx.graphics.getHeight() + y + object.getHeight()*2);
			y += object.getHeight()*2;
			gameWorld.addObjectToWorld(object);
		}
	}

}
