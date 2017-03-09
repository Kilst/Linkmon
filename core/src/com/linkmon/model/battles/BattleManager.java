package com.linkmon.model.battles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.MyVector2;
import com.linkmon.model.World;
import com.linkmon.model.WorldRenderingComponent;
import com.linkmon.model.aonevonebattle.moves.MoveFactory;
import com.linkmon.model.components.CollisionComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.libgdx.LinkmonRenderingComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonIds;
import com.linkmon.model.linkmon.LinkmonPhysicsComponent;
import com.linkmon.model.linkmon.animations.AnimationStateIdle;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.model.linkmon.animations.battle.AnimationStateWalkTo;
import com.linkmon.model.minigames.PlayableInputComponent;
import com.linkmon.model.minigames.PlayablePhysicsComponent;
import com.linkmon.view.UIRenderer;

public class BattleManager {
	
	World battleWorld;
	
	private IBattle battle;
	
	int turnCounter = 0;	
	
	private boolean playRound = false;
	private boolean battleEnded = false;
	
	GameObject[] linkmons;
	GameObject currentLinkmon;
	List<GameObject> linkmonsSorted;	
	
	private EventManager eManager;
	
	public BattleManager(IBattle battle, EventManager eManager) {
		this.eManager = eManager;
		
		this.battle = battle;
		linkmonsSorted = new ArrayList<GameObject>();
		linkmons = battle.getLinkmonArray();
		
		battleEnded = false;
		
		// Create new World for battle to take place.
		battleWorld = new BattleWorld(eManager, UIRenderer.WIDTH, UIRenderer.HEIGHT);
		battleWorld.addRenderer(new WorldRenderingComponent(battleWorld, "battleBackground"));
		((WorldRenderingComponent)battleWorld.getRenderer()).setBackground();
		
		// Add linkmons to World
		battleWorld.addObjectToWorld(linkmons[0]);
		battleWorld.addObjectToWorld(linkmons[1]);
		battleWorld.addObjectToWorld(linkmons[2]);
		battleWorld.addObjectToWorld(linkmons[3]);
		battleWorld.addObjectToWorld(linkmons[4]);
		battleWorld.addObjectToWorld(linkmons[5]);
		battleWorld.update();
		Gdx.app.log("LocalBattle", "Objects: " + battleWorld.getObjects().size());
		
		// Set all animations to Idle, and add gameObject to a new list
		for(int i = 0; i < battleWorld.getObjects().size(); i++) {
			((LinkmonAnimationComponent) ((LibgdxRenderingComponent)linkmons[i].getRenderer()).getAnimationComponent()).setState(new AnimationStateIdle(((LinkmonAnimationComponent) ((LibgdxRenderingComponent)linkmons[i].getRenderer()).getAnimationComponent())));
			linkmonsSorted.add(linkmons[i]);
		}
		
		// Sort list by speed
		Collections.sort(linkmonsSorted, new SpeedComparator()); // Sort by speed
	}
	
	public void playRound() {
		// Start a new round
		turnCounter = 0;
		currentLinkmon = linkmonsSorted.get(turnCounter);
		playRound = true;
		playTurn();
	}
	
	public void playTurn() {
		
		// Current Linkmons turn
		
		if(turnCounter <= 5) {
			
			Gdx.app.log("LocalBattle", "TURN: " + turnCounter);
			
			((BattleExtraComponent)currentLinkmon.getExtraComponents()).playTurn();
		}
	}
	
	private void checkWin() {
		// You win
		if(((BattleExtraComponent)linkmons[0].getExtraComponents()).isDead() &&
				((BattleExtraComponent)linkmons[1].getExtraComponents()).isDead() &&
				((BattleExtraComponent)linkmons[2].getExtraComponents()).isDead()) {
			turnCounter = 0;
			playRound = false;
			battleEnded = true;
			currentLinkmon = null;
			eManager.notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_ENDED, 0));
		}
		
		// You lose
		else if(((BattleExtraComponent)linkmons[3].getExtraComponents()).isDead() &&
				((BattleExtraComponent)linkmons[4].getExtraComponents()).isDead() &&
				((BattleExtraComponent)linkmons[5].getExtraComponents()).isDead()) {
			turnCounter = 0;
			playRound = false;
			battleEnded = true;
			currentLinkmon = null;
			eManager.notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_ENDED, 1));
		}
	}
	
	private void battleChecks() {
		if(currentLinkmon != null && !((BattleExtraComponent)currentLinkmon.getExtraComponents()).isPlayingTurn() && playRound) {
			eManager.notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_TURN_ENDED));
				checkWin();
				
				if(turnCounter >= 5) {
					turnCounter = 0;
					playRound = false;
				}
				else {
					turnCounter++;
					currentLinkmon = linkmonsSorted.get(turnCounter);
					((BattleExtraComponent)currentLinkmon.getExtraComponents()).setPlayingTurn(true);
					playTurn();
				}
		}
		
		if(playRound == false) {
			eManager.notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_NEW_ROUND));
		}
	}
	
	public void update() {
		// TODO Auto-generated method stub
		battleWorld.update();
		
		if(!battleEnded) {
			battleChecks();
		}
	}

	public void setPlayerMove(int playerMove) {
		battle.setPlayerMove(playerMove);
	}
	
	public void setPlayerTarget(int playerTarget) {
		battle.setPlayerTarget(playerTarget);
	}

	public void setOpponentMove(int opponentMove) {
		
	}

	public void setOpponentTarget(int opponentTarget) {
		
	}
	
	public World getWorld() {
		// TODO Auto-generated method stub
		return battleWorld;
	}

	public GameObject[] getLinkmons() {
		// TODO Auto-generated method stub
		return linkmons;
	}

	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return playRound;
	}
}
