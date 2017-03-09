package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.aonevonebattle.OneVBattlePhysicsComponent;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.AnimationStateWalk;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.view.particles.ParticleIds;

public class PhysicalMoveAction implements IMoveAction {
	
	// Defines how the player object's physics will behave during the move
	// Updates enemy health and player energy
	// Sets animations and particles
	
	// doAction is looped through, hence the checks..

	private boolean running = false;
	
	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		if(running) {
			
			if(player.getX() == ((OneVBattlePhysicsComponent)player.getPhysicsComponent()).getDashToX()) {
	
				// update animation
				if(!(animComp.getState() instanceof AnimationStateAttack))
					animComp.setState(new AnimationStateAttack(animComp));
				
				// apply damage & return home (check attack animation has ended)
				if(animComp.isCurrentAnimationEnded()) {
					// apply damage
					((OneVOneBattleComponent)target.getExtraComponents()).updateHealth();
					
					// Return home
					animComp.setState(new AnimationStateWalk(animComp));
					OneVBattlePhysicsComponent physicsComponent = ((OneVBattlePhysicsComponent)player.getPhysicsComponent());
					physicsComponent.dashTo(physicsComponent.getHomeX(), (int)player.getY(), player, 20);
					
					// Stop updating
					Gdx.app.log("Move", "running = false");
					running = false;
					((OneVOneBattleComponent)player.getExtraComponents()).setEnergy(energy);
					player.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.DAMAGED, target.getX()+target.getWidth()/2, target.getY()+target.getHeight()/2));
					return false;
				}
			}
		}
		
		else if(!running && player.getX() == ((OneVBattlePhysicsComponent)player.getPhysicsComponent()).getDashToX()) {
			Gdx.app.log("Move", "player turn = false");
			((OneVOneBattleComponent)player.getExtraComponents()).setMoveEnded(true);
		}
		
		return true;
	}

	@Override
	public void setRunning(boolean value) {
		// TODO Auto-generated method stub
		running = value;
	}

	@Override
	public boolean start(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp) {
		// TODO Auto-generated method stub
		
		((OneVBattlePhysicsComponent)player.getPhysicsComponent()).dashTo((int)(target.getX()+(target.getWidth()*target.direction)), (int)player.getY(), player, 20);
		
		animComp = (LinkmonAnimationComponent) ((LibgdxRenderingComponent)player.getRenderer()).getAnimationComponent();
		animComp.setState(new AnimationStateWalk(animComp));
		
		running = true;
		
		return false;
	}

}
