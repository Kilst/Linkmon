package com.linkmon.model.linkmon.animations.battle;

import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.battles.BattleLinkmonRenderingComponent;
import com.linkmon.model.battles.BeamAttackRenderingComponent;
import com.linkmon.model.battles.MoveParticleRenderingComponent;
import com.linkmon.model.battles.MovePhysicsComponent;
import com.linkmon.model.battles.MoveRenderingComponent;
import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.linkmon.animations.AnimationStateIdle;
import com.linkmon.model.linkmon.animations.AnimationStateWalk;
import com.linkmon.model.linkmon.animations.BaseAnimationState;
import com.linkmon.view.particles.ParticleIds;

public class AnimationStateWalkTo extends BaseAnimationState {
	
	float moveToX;
	float moveToY;
	
	boolean returningHome;

	public AnimationStateWalkTo(IAnimationComponent animComp, float moveToX, float moveToY, boolean returningHome) {
		super(animComp);
		// TODO Auto-generated constructor stub
		this.moveToX = moveToX;
		this.moveToY = moveToY;
		
		this.returningHome = returningHome;
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		
		// Update energy once we start walking home
		if(returningHome)
			((BattleExtraComponent)object.getExtraComponents()).updateEnergy(((BattleExtraComponent)object.getExtraComponents()).getBattleLinkmon().getEnergy());
		
		// If we get to our target position, create attack and addToWorld
		if(!returningHome && (object.getX() == moveToX && object.getY() == moveToY) || ((BattleExtraComponent)object.getExtraComponents()).getStatus().isDefending()){
			BattleExtraComponent component = ((BattleExtraComponent)object.getExtraComponents());
			// If attacking
			if(component.getStatus().isAttacking()) {
				// Attack animation
				animationComp.setState(new AnimationStateAttack(animationComp));
				// Create new move gameobject
				GameObject move = new GameObject(ObjectId.ATTACK_BEAM, ObjectType.MOVE, new MoveParticleRenderingComponent(), null, new MovePhysicsComponent(), null);
				
				move.setPosition(object.getX() + object.getWidth()/2, object.getY() + object.getHeight()/2);
				
				// Get angle and distance from position, to enemy position
				((MoveParticleRenderingComponent)move.getRenderer()).setAngle(component.getTarget().getX()+(component.getTarget().getWidth()/2), component.getTarget().getY()+(component.getTarget().getHeight()/2), move);
				// Create particle effect
				((MoveParticleRenderingComponent)move.getRenderer()).setParticleEffect(component.getCurrentMove().getType(), object.getX()+(object.getWidth()/2), object.getY()+(object.getHeight()/2));
				// Add to World
				object.getWorld().addObjectToWorld(move);
			}
			// Check and create new casting, defending or idle animations
			else if(component.getStatus().isCasting())
				animationComp.setState(new AnimationStateCast(animationComp));
			else if(component.getStatus().isDefending())
				animationComp.setState(new AnimationStateDefend(animationComp));
			else
				animationComp.setState(new AnimationStateIdle(animationComp));
		}
		else if(returningHome && (object.getX() == moveToX && object.getY() == moveToY)) {
			// Called once the object is back at its home position
			
			 // Idle
			animationComp.setState(new AnimationStateIdle(animationComp));
			 // Turn ended
			((BattleExtraComponent)object.getExtraComponents()).setPlayingTurn(false);
			// Update Health (poison only)
			((BattleExtraComponent)object.getExtraComponents()).getBattle().updatePoison(((BattleExtraComponent)object.getExtraComponents()));
			((BattleExtraComponent)object.getExtraComponents()).updatePoisonDamage();
			// Reset direction since we are facing the opposite way now
			object.direction = object.direction*-1;
		}
	}

}
