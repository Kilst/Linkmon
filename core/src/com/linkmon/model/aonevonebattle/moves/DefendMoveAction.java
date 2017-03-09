package com.linkmon.model.aonevonebattle.moves;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.aonevonebattle.OneVBattleRenderingComponent;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.battles.BattleLinkmonRenderingComponent;
import com.linkmon.model.battles.MoveParticleRenderingComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.view.particles.ParticleIds;

public class DefendMoveAction implements IMoveAction {

	private boolean running = false;
	
	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		if(running) {
			
			// update animation
			if(!(animComp.getState() instanceof AnimationStateAttack)) {
				animComp.setState(new AnimationStateAttack(animComp));
				((OneVBattleRenderingComponent)player.getRenderer()).setParticleEffect(ParticleIds.DEFEND, player.getX()+player.getWidth()/2, player.getY());
			}
			
			// apply damage
			if(animComp.isCurrentAnimationEnded()) {
				// apply damage
//				((OneVOneBattleComponent)target.getExtraComponents()).updateHealth();

				// Stop updating
				running = false;
				Gdx.app.log("Move", "running = false");
				
				((OneVOneBattleComponent)player.getExtraComponents()).setEnergy(energy);
				((OneVOneBattleComponent)player.getExtraComponents()).setDefending(true);
				((OneVOneBattleComponent)player.getExtraComponents()).setMoveEnded(true);
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
		return false;
	}

}
