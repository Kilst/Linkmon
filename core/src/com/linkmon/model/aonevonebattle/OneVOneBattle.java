package com.linkmon.model.aonevonebattle;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.helpers.Timer;
import com.linkmon.model.World;
import com.linkmon.model.WorldRenderingComponent;
import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.battles.BattleLinkmonRenderingComponent;
import com.linkmon.model.battles.BattleManager;
import com.linkmon.model.battles.BattlePhysicsComponent;
import com.linkmon.model.battles.BattleWorld;
import com.linkmon.model.battles.DamageFormula;
import com.linkmon.model.battles.IBattle;
import com.linkmon.model.battles.LocalBattleLinkmon;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.AnimationStateIdle;
import com.linkmon.view.particles.ParticleIds;

public class OneVOneBattle implements IBattles {
	
	OneVOneBattleComponent p1;
	OneVOneBattleComponent p2;
	GameObject player1;
	GameObject player2;
	
	BattleWorld world;
	
	OneVOneBattleComponent[] players;
	
	int playerCounter = 0;
	
	private boolean playingRound = false;
	
	private boolean battleEnded = false;
	
	boolean usingItem = false;
	
	private int opponentId;
	
	private Timer startOfTurnTimer;

	@Override
	public void setupBattle(GameObject player, GameObject opponent, int opponentId, World world, EventManager eManager) {
		// TODO Auto-generated method stub
		
		startOfTurnTimer = new Timer(1, false);
		
		this.opponentId = opponentId;
		
		this.world = (BattleWorld) world;
		
		battleEnded = false;
		playingRound = false;
		
		p1 = new OneVOneBattleComponent(player, eManager);
		p2 = new OneVOneBattleComponent(opponent, eManager);
				
		player1 = new GameObject(player.getId(), ObjectType.LINKMON, new OneVBattleRenderingComponent(), null,
				new OneVBattlePhysicsComponent(), p1);
		
		player1.setPosition(110, 180);
		player1.direction = Direction.RIGHT;
		
		((OneVBattlePhysicsComponent)player1.getPhysicsComponent()).setHomePositions(player1);
		
		((LibgdxRenderingComponent)player1.getRenderer()).setAnimation(new LinkmonAnimationComponent(player1.getId()));
		((LinkmonAnimationComponent) ((LibgdxRenderingComponent)player1.getRenderer()).getAnimationComponent()).setState(new AnimationStateIdle(((LinkmonAnimationComponent) ((LibgdxRenderingComponent)player1.getRenderer()).getAnimationComponent())));
		
		player2 = new GameObject(opponent.getId(), ObjectType.LINKMON, new OneVBattleRenderingComponent(), null,
				new OneVBattlePhysicsComponent(), p2);
		
		player2.setPosition(1010, 180);
		player2.direction = Direction.LEFT;
		
		((OneVBattlePhysicsComponent)player2.getPhysicsComponent()).setHomePositions(player2);
		
		((LibgdxRenderingComponent)player2.getRenderer()).setAnimation(new LinkmonAnimationComponent(player2.getId()));
		((LinkmonAnimationComponent) ((LibgdxRenderingComponent)player2.getRenderer()).getAnimationComponent()).setState(new AnimationStateIdle(((LinkmonAnimationComponent) ((LibgdxRenderingComponent)player2.getRenderer()).getAnimationComponent())));
		
		p1.setPlayer(player1);
		p1.setOpponent(player2);
		
		p2.setPlayer(player2);
		p2.setOpponent(player1);
		
//		world = new BattleWorld(eManager, 1280, 720);
//		world.addRenderer(new WorldRenderingComponent(world, "battleBackground"));
//		((WorldRenderingComponent)world.getRenderer()).setBackground();
		
		world.addObjectToWorld(player1);
		world.addObjectToWorld(player2);
		
		players = new OneVOneBattleComponent[2];
		
		if(p1.getSpeed() > p2.getSpeed()) {
			players[0] = p1;
			players[1] = p2;
		}
		else {
			players[0] = p2;
			players[1] = p1;
		}
	}
	
//	public void setUsingItem(boolean using) {
//		usingItem = using;
//	}

	@Override
	public void playRound() {
		// TODO Auto-generated method stub
		
		//speedComparator();
		
		AISetMove();
		
		// First player
		preCalculateBattle(players[0], players[1]);
		// Second player
		preCalculateBattle(players[1], players[0]);
		
		// Start the round
		playingRound = true;
		// Counter used to increment the players in update()
		playerCounter = 0; // Reset for new move
		
		// Start timer to delay the start of each turn (gives text and effects time to complete[aesthetics only])
		startOfTurnTimer.start();
		startOfTurnTimer.restart();
	}
	
	private void preCalculateBattle(OneVOneBattleComponent current, OneVOneBattleComponent target) {
		// Increase status counters, check/clear status', set/clear battle modifiers & set worn off messages
		current.getStatus().preMoveChecks();
		// Set the damage, inclusive of damage reduction effect
		target.setDamage((int) (current.getStatus().getStunnedPercent()*DamageFormula.damage(1, current.getAttack(), target.getDefense(), current.getCurrentMove().getDamage(), target.isDefending())));
		// Set if we missed our target (inclusive of hit chance reduction effect). Sets target.damageTaken to 0 if true
		target.setMiss(target.missCheck(current.getSpeed()*current.getStatus().getDarknessPercent()));
		// Check if the current move has a status effect, & if we've hit the target.
		if(current.getCurrentMove().getStatusEffect() != null && !target.missed()) {
			// Random to see if we apply the status effect (we get the % chance [as a float] from the status effect)
			if(current.getStatus().roll(current.getCurrentMove().getStatusEffect().getChance()))
				// Apply status to target, set effect applied message
				current.getCurrentMove().applyStatusEffect(target.getStatus());
		}
	}

	@Override
	public void checkWin() {
		// TODO Auto-generated method stub
		if(p1.getHealth() < 1) {
			battleEnded = true;
			world.geteManager().notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_ENDED, 1)); // Lose
		}
		else if(p2.getHealth() < 1) {
			battleEnded = true;
			world.geteManager().notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_ENDED, 0)); // Win
		}
	}

	@Override
	public BattleWorld getWorld() {
		// TODO Auto-generated method stub
		return world;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		if(!battleEnded) {
			
			if(p1 != null) { // Should just create things in the constructor. But the check is there because it updates before I've created the objects in setupBattle()
				// Update battle components
				p1.update(player1);
				p2.update(player2);
			}
			
			// 
			if(playingRound) {
				
				checkWin();
				
				if(playerCounter < 2 && (startOfTurnTimer.checkTimer() || playerCounter == 0)) {
					
					if(!players[playerCounter].isPlayingTurn()) {
						
//						players[playerCounter].getStatus().showRemovedStatus(players[playerCounter]);
						players[playerCounter].setCycleWornOffMessages(true);
						
						if(players[playerCounter] == p2) {
							playTurn(p2, p1);
						}
						else {
							playTurn(p1, p2);
						}
					}
					else if(players[playerCounter].isMoveEnded() && players[playerCounter].isPlayingTurn()) {
						players[playerCounter].setPlayingTurn(false);
						startOfTurnTimer.restart();
						
//						if(players[playerCounter] == p2) {
//							p1.getStatus().showHitStatus(p1);
//							if(p1.getStatus().isDarkness())
//								((OneVBattleRenderingComponent)p1.getPlayer().getRenderer()).setContinuousParticleEffect(ParticleIds.DARKNESS, p1.getPlayer().getX()+p1.getPlayer().getHalfWidth(), p1.getPlayer().getY()+p1.getPlayer().getHalfHeight());
//						}
//						else {
//							p2.getStatus().showHitStatus(p2);
//							if(p2.getStatus().isDarkness())
//								((OneVBattleRenderingComponent)p2.getPlayer().getRenderer()).setContinuousParticleEffect(ParticleIds.DARKNESS, p2.getPlayer().getX()+p2.getPlayer().getHalfWidth(), p2.getPlayer().getY()+p2.getPlayer().getHalfHeight());
//						}


						playerCounter++;
					}
					
					if(playerCounter == 2) {
						playingRound = false;
						world.geteManager().notify(new ModelEvent(ModelEvents.LOCAL_BATTLE_NEW_ROUND));
					}
				}
			}			
		}
	}
	
	private void playTurn(OneVOneBattleComponent current, OneVOneBattleComponent target) {
		
		current.setPlayingTurn(true);
		current.setMoveEnded(false);
		
		if(!current.isMoveEnded()) {
			current.playTurn();
		}
	}
	
	public void AISetMove() {
		int choice = (int) Math.ceil(Math.random()*4);
		p2.setCurrentMove(choice);
	}

	public GameObject getPlayer() {
		// TODO Auto-generated method stub
		return player1;
	}

	public int getOpponentId() {
		return opponentId;
	}

	public void setOpponentId(int opponentId) {
		this.opponentId = opponentId;
	}

}
