package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.linkmon.helpers.Timer;
import com.linkmon.model.aonevonebattle.OneVBattlePhysicsComponent;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.battles.MoveParticleRenderingComponent;
import com.linkmon.model.battles.MovePhysicsComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.view.particles.ParticleIds;

public class BeamAttackMoveAction implements IMoveAction {

	private boolean running = false;
	
	GameObject particleObject;
	Timer timer;

	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		
		if(running) {
			
			// update animation
			if(!(animComp.getState() instanceof AnimationStateAttack))
				animComp.setState(new AnimationStateAttack(animComp));
			
			// apply damage
			if(((MoveRenderingComponent)particleObject.getRenderer()).getEffect().isComplete()) {
				// apply damage
				((OneVOneBattleComponent)target.getExtraComponents()).updateHealth();

				// Stop updating
				running = false;
				Gdx.app.log("Move", "running = false");
				((OneVOneBattleComponent)player.getExtraComponents()).setEnergy(energy);
				player.getWorld().removeObjectFromWorld(particleObject);
				particleObject = null;
				timer.start();
			}
		}
		else if(timer.checkTimer())
			((OneVOneBattleComponent)player.getExtraComponents()).setMoveEnded(true);
		
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
		if(particleObject == null)
			createParticleAttack(player, target);
		
		running = true;
		timer = new Timer(1, false);
		
		return false;
	}
	
	private void createParticleAttack(GameObject player, GameObject target) {
		particleObject = new GameObject(1, 0, new MoveRenderingComponent(), null, new OneVBattlePhysicsComponent(), null);
		
		particleObject.direction = player.direction;
		particleObject.setPosition(player.getX()+player.getHalfWidth()+(player.getHalfWidth()*player.direction), player.getY()+player.getHalfHeight());
		((OneVBattlePhysicsComponent)particleObject.getPhysicsComponent()).setAngle(target.getX() + (target.direction*target.getWidth()/2), target.getY()+target.getHalfHeight(), particleObject);
		
		float pad = player.getWidth();
		if(player.direction == Direction.LEFT)
			pad = 0;
		((MoveRenderingComponent)particleObject.getRenderer()).setParticleEffect(((OneVOneBattleComponent)player.getExtraComponents()).getCurrentMove().getParticleId(), player.getX()+pad, player.getY()+player.getHalfHeight());
		
		ParticleEffect eff = ((MoveRenderingComponent)particleObject.getRenderer()).getEffect();
		
		float angle = ((OneVBattlePhysicsComponent)particleObject.getPhysicsComponent()).getAngle();
		// Set angles, rotation, life and duration
				for(ParticleEmitter emitter : eff.getEmitters()) {
					
					// Set angle to target angle
					emitter.getAngle().setHigh(-angle - 45);
					emitter.getAngle().setHighMax(-angle + 45);
					emitter.getAngle().setLow(-angle);
					
					emitter.getRotation().setLow(-angle + 90);
					
//					Vector2 power = getMatrixRotation(500, 0, angle);
//					
//					// Set gravity and wind based on angle
//					emitter.getGravity().setHighMin(-power.x);
//					emitter.getGravity().setHighMax(power.x);
//					emitter.getWind().setHighMin(0);
//					emitter.getWind().setHighMax(-power.y*direction);
					
					// Calculate duration based on distance from points / velocity (*1000 to get to milliseconds)
					float distance = Vector2.dst(particleObject.getX(), particleObject.getY(), target.getX()+target.getHalfWidth(), target.getY());
					int duration = (int) (distance/(600)*1000);
					Gdx.app.log("BeamAttack", "Duration: " + duration + "ms");
					emitter.getDuration().setLow(duration);
					
					emitter.getLife().setLow(0);
					if(emitter.getLife().getHighMin() == 1000 || emitter.getLife().getHighMax() == 1000)
						emitter.getLife().setHigh(duration, duration);
				}
		
				eff.start();
		
		player.getWorld().addObjectToWorld(particleObject);
	}
}
