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
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.Timer;

public class MiniGameController implements ScreenListener {
	
	private Player player;
	
	private World gameWorld;
	
	private Timer spawnTimer;
	GameObject linkmon;
	EventManager eManager;
	
	public MiniGameController(EventManager eManager) {
		this.eManager = eManager;
		gameWorld = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		linkmon = new GameObject(33, ObjectType.ITEM, new LibgdxRenderingComponent(), new PlayableInputComponent(eManager),
				new PlayablePhysicsComponent(new CollisionComponent()), null);
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)linkmon.getRenderer()).setSprite(linkmon);
		linkmon.setX(0);
		linkmon.setY(80);
		
		gameWorld.addObjectToWorld(linkmon);
		
		spawnTimer = new Timer(1, true);
		spawnTimer.start();
		
		eManager.addScreenListener(this);
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

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.MOVE_PLAYER) : {
				((PlayablePhysicsComponent)linkmon.getPhysicsComponent()).addVeloX(event.value);
				Gdx.app.log("MOVE", "MMMisPressed()");
			}
		}
		return false;
	}

}
