package com.linkmon.componentcontroller;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.aminigame.PlayableInputComponent;
import com.linkmon.componentmodel.aminigame.PlayablePhysicsComponent;
import com.linkmon.componentmodel.components.CollisionComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.componentmodel.gameobject.ObjectType;
import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;
import com.linkmon.componentmodel.libgdx.LinkmonAnimationComponent;
import com.linkmon.componentmodel.libgdx.LinkmonRenderingComponent;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonInputComponent;
import com.linkmon.componentmodel.linkmon.LinkmonPhysicsComponent;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.helpers.Timer;

public class MiniGameController {
	
	private Player player;
	
	private World gameWorld;
	
	private Timer spawnTimer;
	
	public MiniGameController(EventManager eManager) {
		gameWorld = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		GameObject linkmon = new GameObject(1, ObjectType.LINKMON, new LinkmonRenderingComponent(), new PlayableInputComponent(eManager),
				new PlayablePhysicsComponent(new CollisionComponent()), null);
		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		linkmon.setX(150);
		
		gameWorld.addObjectToWorld(linkmon);
		
		spawnTimer = new Timer(1, true);
		spawnTimer.start();
	}
	
	public World getWorld() {
		return gameWorld;
	}
	
	public void update() {
		gameWorld.update();
		if(spawnTimer.checkTimer())
			eggSpawner();
	}
	
	private void eggSpawner() {
		GameObject object = ObjectFactory.getInstance().getObjectFromId(77);
		Random rnd = new Random();
		object.setX(rnd.nextInt((int)(gameWorld.getWidth())));
		object.setY(Gdx.graphics.getHeight());
		gameWorld.addObjectToWorld(object);
	}

}
