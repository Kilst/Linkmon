package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.battles.MoveParticleRenderingComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;

public class CastingMoveAction implements IMoveAction {

	private boolean running = false;
	
	GameObject particleObject;

	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		
		if(running) {
			
			// update animation
			if(!(animComp.getState() instanceof AnimationStateAttack))
				animComp.setState(new AnimationStateAttack(animComp));
			
			// apply damage
			if(animComp.isCurrentAnimationEnded() && ((MoveParticleRenderingComponent)particleObject.getRenderer()).getEffect().isComplete()) {
				// apply damage
				((OneVOneBattleComponent)target.getExtraComponents()).updateHealth();

				// Stop updating
				running = false;
				Gdx.app.log("Move", "running = false");
				
				((OneVOneBattleComponent)player.getExtraComponents()).setEnergy(energy);
				
				((OneVOneBattleComponent)player.getExtraComponents()).setPlayingTurn(false);
				
				player.getWorld().removeObjectFromWorld(particleObject);
				
				((OneVOneBattleComponent)player.getExtraComponents()).setMoveEnded(true);
			}
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
		if(particleObject == null)
			createParticleAttack(player, target);
		
		running = true;
		
		return false;
	}
	
	private void createParticleAttack(GameObject player, GameObject target) {
		particleObject = new GameObject(1, 0, new MoveParticleRenderingComponent(), null, null, null);
		
		particleObject.direction = player.direction;
		particleObject.setPosition(player.getX(), player.getY());
		((MoveParticleRenderingComponent)particleObject.getRenderer()).setAngle(target.getX() + (target.direction*target.getWidth()/2), target.getY(), particleObject);
		
		float pad = player.getWidth();
		if(player.direction == Direction.LEFT)
			pad = 0;
		((MoveParticleRenderingComponent)particleObject.getRenderer()).setParticleEffect(1, player.getX()+pad, player.getY()+player.getHeight()/2);
		
		player.getWorld().addObjectToWorld(particleObject);
	}
}
