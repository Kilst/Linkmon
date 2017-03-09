package com.linkmon.model.linkmon.animations.battle;

import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.MyVector2;
import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.components.IAnimationComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.libgdx.LinkmonRenderingComponent;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.animations.AnimationStateIdle;
import com.linkmon.model.linkmon.animations.AnimationStateWalk;
import com.linkmon.model.linkmon.animations.BaseAnimationState;
import com.linkmon.view.particles.ParticleIds;

public class AnimationStateAttack extends BaseAnimationState {
	
	boolean attacked = false;
	
	public AnimationStateAttack(IAnimationComponent animComp) {
		super(animComp);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void update(IAnimationComponent animationComp, GameObject object) {
		// TODO Auto-generated method stub
		
		if(animationComp.isCurrentAnimationEnded() == true) {
			
			animationComp.setState(new AnimationStateIdle(animationComp));
		}
	}

}
