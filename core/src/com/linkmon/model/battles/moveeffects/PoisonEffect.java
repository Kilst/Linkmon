package com.linkmon.model.battles.moveeffects;

import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.items.equipment.IBattleEffect;

public class PoisonEffect implements IBattleEffect {

	@Override
	public void applyEffect(BattleExtraComponent target) {
		// TODO Auto-generated method stub
		target.setPoisoned(true);
	}
}
