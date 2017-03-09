package com.linkmon.model.aonevonebattle.moves;

import com.linkmon.model.aonevonebattle.OneVBattleRenderingComponent;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.animations.battle.AnimationStateAttack;
import com.linkmon.view.particles.ParticleIds;

public class SleepMoveAction implements IMoveAction {

	private boolean running = false;
	
	@Override
	public boolean doAction(GameObject player, GameObject target, int energy, LinkmonAnimationComponent animComp, OneVMove move) {
		// TODO Auto-generated method stub
		if(running) {
			
			// update animation
			if(!(animComp.getState() instanceof AnimationStateAttack)) {
				animComp.setState(new AnimationStateAttack(animComp));
				((OneVBattleRenderingComponent)player.getRenderer()).setParticleEffect(ParticleIds.SLEEP, player.getX()+player.getWidth(), player.getY()+player.getHeight());
			}
			
			// apply damage
			if(animComp.isCurrentAnimationEnded()) {
				// apply damage
//				((OneVOneBattleComponent)target.getExtraComponents()).updateHealth();
				// Stop updating
				((OneVBattleRenderingComponent)player.getRenderer()).setParticleEffect(ParticleIds.SLEEP, player.getX()+player.getWidth(), player.getY()+player.getHeight());
				running = false;
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
