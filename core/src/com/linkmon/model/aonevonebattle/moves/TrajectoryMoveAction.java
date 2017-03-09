package com.linkmon.model.aonevonebattle.moves;

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

public class TrajectoryMoveAction implements IMoveAction {

	private boolean running = false;
	
	private GameObject throwObject;
	private Timer timer;
	private Timer timer2;
	private boolean useGravity = true;
	private int onHitParticleId;
	private int objectId = -1;
	
	public TrajectoryMoveAction(int onHitParticleId, int objectId) {
		this.onHitParticleId = onHitParticleId;
		this.objectId = objectId;
	}

	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		
		if(running) {
			
			// update animation
			if(!(animComp.getState() instanceof AnimationStateAttack))
				animComp.setState(new AnimationStateAttack(animComp));
			
			// Check if move has hit its target
			else if(timer.ended && useGravity && (throwObject.getY() <= target.getY() || throwObject.getX() == target.getX())) {
				timer.start();
				// Play hit particles
				if(onHitParticleId != -1)
					player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, onHitParticleId, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2));
				
				// Update energy
				((OneVOneBattleComponent)player.getExtraComponents()).setEnergy(energy);
				
				// Remove object
				player.getWorld().removeObjectFromWorld(throwObject);
				throwObject = null;
				// Stop running this if()
				
			}
			
			else if(timer.checkTimer()) {
				// apply damage (damage is pre-calculated in OneVBattle.playRound();)
				((OneVOneBattleComponent)target.getExtraComponents()).updateHealth();
				// Play damage particles
				player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.DAMAGED, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2));
				player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.GREEN_STAR, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2));
				timer2.start();
				running = false;
			}
		}
		
		if(timer2.checkTimer()) {
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
		timer = new Timer(1, false);
		timer2 = new Timer(1, false);
		return false;
	}
	
	private void createParticleAttack(GameObject player, GameObject target) {
		throwObject = new GameObject(objectId, 0, new MoveRenderingComponent(), null, new OneVBattlePhysicsComponent(), null);
		
		throwObject.direction = player.direction;
		throwObject.setPosition(player.getX(), player.getY()+1);
		
//		((MoveRenderingComponent)throwObject.getRenderer()).setSprite(throwObject);
		
//		((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).update(throwObject, null);
		((MoveRenderingComponent)throwObject.getRenderer()).setSprite(throwObject);
		((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).setGravity(useGravity);
		((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).setTarget(target.getX(), target.getY());
		float distance = target.getX()-throwObject.getX();
		if(distance < 0)
			distance += target.getX()+target.getWidth()+throwObject.getWidth();
		((OneVBattlePhysicsComponent)throwObject.getPhysicsComponent()).applyTrajectory(distance, 270+(45*throwObject.direction));
		((MoveRenderingComponent)throwObject.getRenderer()).setParticleEffect(((OneVOneBattleComponent)player.getExtraComponents()).getCurrentMove().getParticleId(), throwObject.getX()+throwObject.getWidth()/2, throwObject.getY()+throwObject.getHeight()/2);
		
		player.getWorld().addObjectToWorld(throwObject);
	}
}
