package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.linkmon.helpers.Timer;
import com.linkmon.model.aonevonebattle.OneVBattlePhysicsComponent;
import com.linkmon.model.aonevonebattle.OneVBattleRenderingComponent;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.animations.AnimationStateWave;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.view.particles.ParticleIds;

public class ItemMoveAction implements IMoveAction {

	private boolean running = false;
	
	private Timer timer;
	
	private boolean timerEnded = false;
	
	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		if(running) {
			
			// update animation
			if(!(animComp.getState() instanceof AnimationStateAttack)) {
				animComp.setState(new AnimationStateAttack(animComp));
				player.getWorld().addObjectToWorld(move.getObject());
				move.getObject().setPosition(1280/2, 720/2);
				timer.start();
			}
			
			if(timer.checkTimer() && !timerEnded) {
				OneVBattlePhysicsComponent m = new OneVBattlePhysicsComponent();
				move.getObject().setPhysicsComponent(m);
				m.dashTo((int)player.getX(), (int)player.getY(), move.getObject(), 20);
				timerEnded = true;
			}
			
			// Check item position against player's position
			if(move.getObject().getX() == player.getX() && move.getObject().getY() == player.getY()) {

				//Remove item				
				player.getWorld().removeObjectFromWorld(move.getObject());
				
				// Check if animation ended
				if(animComp.isCurrentAnimationEnded()) {
					// Stop updating
					running = false;
					// Add Particle effect to player linkmon
					((OneVBattleRenderingComponent)player.getRenderer()).setParticleEffect(ParticleIds.HEAL, player.getX()+player.getWidth()/2, player.getY());
					
					if(move.getId() == MoveIds.HEAL) {
						// Apply healing
						((OneVOneBattleComponent)player.getExtraComponents()).setHealing(move.getDamage());
						((OneVOneBattleComponent)player.getExtraComponents()).updateHealing();
					}
					else if(move.getId() == MoveIds.ENERGY_ADD) {
						// Apply healing
						((OneVOneBattleComponent)player.getExtraComponents()).addEnergy(move.getDamage());
					}
					// End turn
					((OneVOneBattleComponent)player.getExtraComponents()).setMoveEnded(true);
				}
			}
		}
		
		return false;
	}

	@Override
	public void setRunning(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean start(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp) {
		// TODO Auto-generated method stub
		running = true;
		timer = new Timer(1, false);
		return false;
	}

}
