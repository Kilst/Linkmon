package com.linkmon.model.items.actions;

import com.linkmon.helpers.CompleteMessage;
import com.linkmon.helpers.CompleteMessageFactory;
import com.linkmon.helpers.CompleteMessageId;
import com.linkmon.model.World;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.aonevonebattle.moves.ItemMoveAction;
import com.linkmon.model.aonevonebattle.moves.OneVMove;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.model.linkmon.MoveType;

public class EnergyPotionItemAction implements IItemAction {
	
	int healAmount;

	public EnergyPotionItemAction(int healAmount) {
		// TODO Auto-generated constructor stub
		this.healAmount = healAmount;
	}

	@Override
	public CompleteMessage use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		try {
			OneVMove move = new OneVMove(MoveIds.ENERGY_ADD, MoveIds.ENERGY_ADD, healAmount, MoveType.SPECIAL, 0, "Energy+", new ItemMoveAction(), null);
			move.setItemObject(item);
			((OneVOneBattleComponent)linkmon.getExtraComponents()).setCurrentMove(move);
			return null;
		} catch(Exception e) {
			return CompleteMessageFactory.getMessageFromId(CompleteMessageId.ONLY_IN_BATTLE_ERROR);
		}
	}

}
