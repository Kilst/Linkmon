package com.linkmon.model.items.actions;

import com.linkmon.helpers.CompleteMessage;
import com.linkmon.helpers.CompleteMessageFactory;
import com.linkmon.helpers.CompleteMessageId;
import com.linkmon.model.World;
import com.linkmon.model.aonevonebattle.OneVBattlePhysicsComponent;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.aonevonebattle.moves.DefendMoveAction;
import com.linkmon.model.aonevonebattle.moves.ItemMoveAction;
import com.linkmon.model.aonevonebattle.moves.OneVMove;
import com.linkmon.model.battles.MovePhysicsComponent;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.libgdx.LinkmonAnimationComponent;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.MoveType;
import com.linkmon.model.linkmon.animations.AnimationStateWave;
import com.linkmon.model.linkmon.animations.battle.AnimationStateCast;

public class HealthPotionItemAction implements IItemAction {
	
	int healAmount;

	public HealthPotionItemAction(int healAmount) {
		// TODO Auto-generated constructor stub
		this.healAmount = healAmount;
	}

	@Override
	public CompleteMessage use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		try {
			OneVMove move = new OneVMove(MoveIds.HEAL, MoveIds.HEAL, MoveType.SPECIAL, healAmount, 0, "Heal", new ItemMoveAction(), null);
			move.setItemObject(item);
			((OneVOneBattleComponent)linkmon.getExtraComponents()).setCurrentMove(move);
		return null;
		} catch(Exception e) {
			return CompleteMessageFactory.getMessageFromId(CompleteMessageId.ONLY_IN_BATTLE_ERROR);
		}
	}

}
