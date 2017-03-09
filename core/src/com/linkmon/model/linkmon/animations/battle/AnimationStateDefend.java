package com.linkmon.model.linkmon.animations.battle;

import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.animations.AnimationStateIdle;
import com.linkmon.model.linkmon.animations.BaseAnimationState;

public class AnimationStateDefend extends BaseAnimationState {

	public AnimationStateDefend(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		if(animationComp.isCurrentAnimationEnded() == true) {

//			((BattleExtraComponent)object.getExtraComponents()).getBattle().updateHealing(((BattleExtraComponent)((BattleExtraComponent)object.getExtraComponents()).getTarget().getExtraComponents()));
//			((BattleExtraComponent)object.getExtraComponents()).getBattle().updateDamage(((BattleExtraComponent)((BattleExtraComponent)object.getExtraComponents()).getTarget().getExtraComponents()));
			((BattleExtraComponent)object.getExtraComponents()).getBattle().updatePoison(((BattleExtraComponent)object.getExtraComponents()));
			((BattleExtraComponent)object.getExtraComponents()).updatePoisonDamage();
			animationComp.setState(new AnimationStateIdle(animationComp));
			((BattleExtraComponent)object.getExtraComponents()).setPlayingTurn(false);
		}
	}
}
