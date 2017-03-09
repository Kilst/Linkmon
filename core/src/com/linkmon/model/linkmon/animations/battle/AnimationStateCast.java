package com.linkmon.model.linkmon.animations.battle;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.battles.BattleLinkmonRenderingComponent;
import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.animations.AnimationStateWalk;
import com.linkmon.model.linkmon.animations.BaseAnimationState;
import com.linkmon.view.particles.ParticleIds;

public class AnimationStateCast extends BaseAnimationState {

	public AnimationStateCast(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		if(animationComp.isCurrentAnimationEnded() == true) {

			if(((BattleExtraComponent)object.getExtraComponents()).getCurrentMove().getId() == MoveIds.HEAL) {
				//((BattleExtraComponent)((BattleExtraComponent)object.getExtraComponents()).getTarget().getExtraComponents()).updateHealing();
				((BattleExtraComponent)object.getExtraComponents()).getBattle().updateHealing(((BattleExtraComponent)((BattleExtraComponent)object.getExtraComponents()).getTarget().getExtraComponents()));	
				//((BattleLinkmonRenderingComponent)((BattleExtraComponent)object.getExtraComponents()).getTarget().getRenderer()).getAnimationComponent().setState(new AnimationStateHealed(animationComp));
				
				Gdx.app.log("ANIMATIONSTATECAST", "Healing");
			}
			else {
				((BattleExtraComponent)((BattleExtraComponent)object.getExtraComponents()).getTarget().getExtraComponents()).updateDamage();
				Gdx.app.log("ANIMATIONSTATECAST", "Damaging");
			}
			
			object.getPhysicsComponent().setMoveTo(((BattleExtraComponent)object.getExtraComponents()).getStartX(), ((BattleExtraComponent)object.getExtraComponents()).getStartY());
			animationComp.setState(new AnimationStateWalkTo(animationComp, ((BattleExtraComponent)object.getExtraComponents()).getStartX(), ((BattleExtraComponent)object.getExtraComponents()).getStartY(), true));
		}
	}

}
