package com.linkmon.model.minigames.coinroll;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.model.World;
import com.linkmon.model.WorldRenderingComponent;
import com.linkmon.model.components.CircleCollisionComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.minigames.IMiniGame;
import com.linkmon.model.minigames.PlayableInputComponent;
import com.linkmon.model.minigames.PlayablePhysicsComponent;

public class CoinRoll implements IMiniGame, ModelListener, ScreenListener {
	
	public boolean gameEnded = false;
	public boolean gameStarted = false;
	
	private World gameWorld;
	
	GameObject linkmon;
	
	ObjectSpawner spawner;
	
	EventManager eManager;
	
	public CoinRoll(EventManager eManager) {
		this.eManager = eManager;
		gameWorld = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameWorld.addRenderer(new WorldRenderingComponent(gameWorld, "UI/ringGrabBackground.png", true));
		
		worldSetup();
		
		eManager.addModelListener(this);
		eManager.addScreenListener(this);
	}
	
	private void worldSetup() {
		linkmon = new GameObject(7, ObjectType.LINKMON, new LibgdxRenderingComponent(), new PlayableInputComponent(eManager),
				new CoinRollLinkmonPhysicsComponent(new CircleCollisionComponent(), false, true), null);
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)linkmon.getRenderer()).setSprite(linkmon);
		linkmon.setX(0);
		linkmon.setY(80);
		
		gameWorld.addObjectToWorld(linkmon);
		linkmon.direction = Direction.RIGHT;
		
		spawner = new ObjectSpawner(gameWorld);
		
		gameWorld.setUpdating(true);
	}
	
	public World getWorld() {
		return gameWorld;
	}
	
	public void update() {
		
		if(!gameEnded) {
			if(gameStarted)
				spawner.update();
		}
	}

	@Override
	public GameObject getPlayer() {
		// TODO Auto-generated method stub
		return linkmon;
	}

	@Override
	public void movePlayer(float x, float y) {
		// TODO Auto-generated method stub
		if(gameStarted)
			((PlayablePhysicsComponent)linkmon.getPhysicsComponent()).addVeloX(x);
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		gameWorld.getObjects().clear();
		worldSetup();
		gameEnded = false;
		gameWorld.setUpdating(true);
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ModelEvents.MINIGAME_WIN): {
				gameEnded = true;
				gameStarted = false;
				gameWorld.setUpdating(false);
				break;
			}
			case(ModelEvents.MINIGAME_LOSE): {
				gameEnded = true;
				gameStarted = false;
				gameWorld.setUpdating(false);
				break;
			}
		}
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.START_MINIGAME) : {
				gameStarted = true;
				break;
			}
		}
		return false;
	}

}
