package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.helpers.Timer;
import com.linkmon.model.aonevonebattle.OneVBattlePhysicsComponent;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.view.particles.ParticleIds;

public class OneTargetThrowObjectMoveAction implements IMoveAction {

	private boolean running = false;
	
	private GameObject throwObject;
	private Timer timer;
	private Timer timer2;

	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		
		if(running) {
			
			// update animation
			if(!(animComp.getState() instanceof AnimationStateAttack))
				animComp.setState(new AnimationStateAttack(animComp));
			
			// Check if move has hit its target
			if(throwObject.getX() == (int)(target.getX()+(target.getWidth()/2)) && throwObject.getY() == (int)(target.getY()+(target.getHeight()/2)) && timer.stopped) {
				timer.start(); // Start timer once move hits target (gives the particles a little time to run)
			}
			
			if(timer.checkTimer()) {
				timer2.start();
				// apply damage (damage is pre-calculated in OneVBattle.playRound();)
				((OneVOneBattleComponent)target.getExtraComponents()).updateHealth();
				// Play damage particles
				player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.DAMAGED, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2));
				player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.GREEN_STAR, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2));
				// Update energy
				((OneVOneBattleComponent)player.getExtraComponents()).setEnergy(energy);
				
				// Remove object
				player.getWorld().removeObjectFromWorld(throwObject);
				throwObject = null;
				// Stop running this if()
				running = false;
			}
		}
		else if(timer2.checkTimer()) {
			// Stop updating
			((OneVOneBattleComponent)player.getExtraComponents()).setMoveEnded(true);
			throwObject = null;
		}
		
		return false;
	}

	@Override
	public void setRunning(boolean value) {
		// TODO Auto-generated method stub
		running = value;
		
	}

	@Override
	public boolean start(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp) {
		// TODO Auto-generated method stub
		if(throwObject == null)
			createParticleAttack(player, target);
		
		running = true;
		timer = new Timer(4, false);
		timer2 = new Timer(2, false);
		
		return false;
	}
	
	private void createParticleAttack(GameObject player, GameObject target) {
		throwObject = new GameObject(ObjectId.MEAT, 0, new MoveRenderingComponent(), null, new OneVBattlePhysicsComponent(), null);
		
		throwObject.direction = player.direction;
		throwObject.setPosition(player.getX()+player.getHalfWidth(), player.getY()+player.getHalfHeight());
		
//		((MoveRenderingComponent)throwObject.getRenderer()).setSprite(throwObject);
		
//		((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).update(throwObject, null);
		
		((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).dashTo((int)(target.getX()+target.getHalfWidth()), (int)(target.getY()+target.getHalfHeight()), throwObject, 5);
		
		((MoveRenderingComponent)throwObject.getRenderer()).setParticleEffect(((OneVOneBattleComponent)player.getExtraComponents()).getCurrentMove().getParticleId(), throwObject.getX()+throwObject.getWidth()/2, throwObject.getY()+throwObject.getHeight()/2);
		
		player.getWorld().addObjectToWorld(throwObject);
	}

}
