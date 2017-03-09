package com.linkmon.model.minigames.duckracing;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.Timer;
import com.linkmon.model.World;
import com.linkmon.model.WorldRenderingComponent;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.minigames.IMiniGame;
import com.linkmon.model.minigames.PlayableInputComponent;
import com.linkmon.model.minigames.PlayablePhysicsComponent;

public class DuckRacing implements IMiniGame, ModelListener, ScreenListener {
	
	public boolean gameEnded = false;
	public boolean gameStarted = false;
	
	private World gameWorld;
	
	GameObject playerDuck;
	GameObject opp1Duck;
	GameObject opp2Duck;
	
	GameObject wave;
	GameObject wave1;
	GameObject wave2;
	
	GameObject finishLine;
	
	EventManager eManager;
	
	public DuckRacing(EventManager eManager) {
		this.eManager = eManager;
		gameWorld = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameWorld.addRenderer(new WorldRenderingComponent(gameWorld, "Background"));
		
		worldSetup();
		
		eManager.addModelListener(this);
		eManager.addScreenListener(this);
	}
	
	private void worldSetup() {
		playerDuck = new GameObject(ObjectId.DUCK, ObjectType.PLAYER, new LibgdxRenderingComponent(), new PlayableInputComponent(eManager),
				new DuckPhysicsComponent(new CollisionComponent(), false, true), null);
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)playerDuck.getRenderer()).setSprite(playerDuck);
		playerDuck.setX(0);
		playerDuck.setY(80);
		playerDuck.direction = Direction.RIGHT;
		
		opp1Duck = new GameObject(ObjectId.DUCK, ObjectType.ITEM, new LibgdxRenderingComponent(), new PlayableInputComponent(eManager),
				new DuckPhysicsComponent(new CollisionComponent(), false, true), new DuckAIExtraComponent((int) Math.round((Math.random()*2))));
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)opp1Duck.getRenderer()).setSprite(opp1Duck);
		opp1Duck.setX(0);
		opp1Duck.setY(160);
		opp1Duck.direction = Direction.RIGHT;
		
		opp2Duck = new GameObject(ObjectId.DUCK, ObjectType.ITEM, new LibgdxRenderingComponent(), new PlayableInputComponent(eManager),
				new DuckPhysicsComponent(new CollisionComponent(), false, true), new DuckAIExtraComponent((int) Math.round((Math.random()*2))));
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)opp2Duck.getRenderer()).setSprite(opp2Duck);
		opp2Duck.setX(0);
		opp2Duck.setY(240);
		opp2Duck.direction = Direction.RIGHT;
		
		wave2 = new GameObject(ObjectId.WAVE, ObjectType.ITEM, new LibgdxRenderingComponent(), null, new WavePhysicsComponent(), null);
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)wave2.getRenderer()).setSprite(wave2);
		wave2.setX(-150);
		wave2.setY(0);
		wave2.direction = Direction.RIGHT;
		((WavePhysicsComponent)wave2.getPhysicsComponent()).setOrigin(wave2.getX(), wave2.getY());
		
		wave1 = new GameObject(ObjectId.WAVE, ObjectType.ITEM, new LibgdxRenderingComponent(), null, new WavePhysicsComponent(), null);
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)wave1.getRenderer()).setSprite(wave1);
		wave1.setX(-150);
		wave1.setY(-80);
		wave1.direction = Direction.RIGHT;
		((WavePhysicsComponent)wave1.getPhysicsComponent()).setOrigin(wave1.getX(), wave1.getY());
		
		wave = new GameObject(ObjectId.WAVE, ObjectType.OBJECT, new LibgdxRenderingComponent(), null, new WavePhysicsComponent(), null);
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)wave.getRenderer()).setSprite(wave);
		wave.setX(-150);
		wave.setY(-160);
		wave.direction = Direction.RIGHT;
		((WavePhysicsComponent)wave.getPhysicsComponent()).setOrigin(wave.getX(), wave.getY());
		
		finishLine = new GameObject(ObjectId.FINISH_LINE, ObjectType.GOAL, new LibgdxRenderingComponent(), null, null, null);
//		((LibgdxRenderingComponent)linkmon.getRenderer()).setAnimation(new LinkmonAnimationComponent(linkmon.getId()));
		((LibgdxRenderingComponent)finishLine.getRenderer()).setSprite(finishLine);
		finishLine.setX(1280-finishLine.getWidth());
		finishLine.setY(0);
		finishLine.direction = Direction.RIGHT;
		
		gameWorld.addObjectToWorld(opp2Duck);
		gameWorld.addObjectToWorld(wave2);
		gameWorld.addObjectToWorld(opp1Duck);
		gameWorld.addObjectToWorld(wave1);
		gameWorld.addObjectToWorld(playerDuck);
		gameWorld.addObjectToWorld(wave);
		
		gameWorld.addObjectToWorld(finishLine);
	}
	
	public World getWorld() {
		return gameWorld;
	}
	
	public void update() {

	}

	@Override
	public GameObject getPlayer() {
		// TODO Auto-generated method stub
		return playerDuck;
	}

	@Override
	public void movePlayer(float x, float y) {
		// TODO Auto-generated method stub
		if(gameStarted)
			((PlayablePhysicsComponent)playerDuck.getPhysicsComponent()).addVeloX(3);
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
				((DuckAIExtraComponent)opp1Duck.getExtraComponents()).setBegin(true);
				((DuckAIExtraComponent)opp2Duck.getExtraComponents()).setBegin(true);
				break;
			}
		}
		return false;
	}

}
