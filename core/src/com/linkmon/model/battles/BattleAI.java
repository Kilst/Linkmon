package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.aonevonebattle.moves.MoveFactory;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.MoveIds;

public class BattleAI {
	
	// targetAdd is either 0 or 3. 3 is player, 0 is opponent
	// there are 6 Linkmons in linkmons[] and opponent is 0 - 2. Hence why we add 3 on opponents turn.

	public static void runAI(LocalBattleLinkmon linkmon, LocalBattleLinkmon[] battleLinkmons, int targetAdd) {
		// TODO Auto-generated method stub
		if(defendCheck(linkmon)) return;
		
		if(!checkTeamsHealth(linkmon, battleLinkmons, targetAdd)) {
			targetEnemy(linkmon, battleLinkmons, targetAdd);
			getMove(linkmon);
		}
	}
	
	private static boolean defendCheck(LocalBattleLinkmon linkmon) {
		
		if(Math.random()*100 > 70) {
			linkmon.setCurrentMove(MoveFactory.getMoveFromId(MoveIds.DEFEND));
			linkmon.setDefending(true);
			Gdx.app.log("BattleAI", "Defending");
			return true;
		}
		return false;
	}
	
	private static boolean checkTeamsHealth(LocalBattleLinkmon linkmon, LocalBattleLinkmon[] battleLinkmons, int targetAdd) {
		
		if(linkmon.getEnergy() < Math.abs(MoveFactory.getMoveFromId(MoveIds.HEAL).getEnergy()))
			return false;
		// if health < 70%, target and setMove(heal);
		for(int i = 3-targetAdd; i < 6-targetAdd; i++) {
			
			float ratio = (float)battleLinkmons[i].getHealth()/battleLinkmons[i].getMaxHealth();

			if(ratio < 0.6f && !battleLinkmons[i].isDead()) {
				linkmon.setTarget(battleLinkmons[i]);
				linkmon.setOpponentTargetId(i);
				linkmon.setCurrentMove(MoveFactory.getMoveFromId(MoveIds.HEAL));
				Gdx.app.log("BattleAI - HEALING", "Health ratio: " + ratio + " Max Health: " + battleLinkmons[i].getMaxHealth() + " Health: " + battleLinkmons[i].getHealth());
				return true;
			}
		}
		
		return false;
	}
	
	private static void targetEnemy(LocalBattleLinkmon linkmon, LocalBattleLinkmon[] battleLinkmons, int targetAdd) {
		
		int target = (int) Math.floor(Math.random()*3+targetAdd);
		// Loop random while target is dead
		while(battleLinkmons[target].isDead())
			target = (int) Math.floor(Math.random()*3+targetAdd);
		
//		// Target main target check
//		if(Math.random()*100 > 1 && ((BattleExtraComponent)linkmon.getExtraComponents()).getTarget() != null && !((BattleExtraComponent)((BattleExtraComponent)linkmon.getExtraComponents()).getTarget().getExtraComponents()).isDead()) {
//			// Set Target
//			((BattleExtraComponent)linkmon.getExtraComponents()).setTarget(((BattleExtraComponent)linkmons[4-targetAdd].getExtraComponents()).getTarget()); // 0 - 2
//		}
//		else
			// Set Target
		linkmon.setTarget(battleLinkmons[target]); // 0 - 2
		linkmon.setOpponentTargetId(target);
		Gdx.app.log("BattleAI", "Target: " + (target));
	}
	
	private void checkOpponentsTeamsHealth() {
		
	}
	
	private static void getMove(LocalBattleLinkmon linkmon) {
//		((BattleExtraComponent)linkmon.getExtraComponents()).setCurrentMove(MoveFactory.getMoveFromId((int) Math.ceil((Math.random()*2)+11))); // 11 - 13
		
		linkmon.setCurrentMove(MoveFactory.getMoveFromId((int) Math.ceil((Math.random()*5)+10)));
		Gdx.app.log("BattleAI", "Move: " + linkmon.getCurrentMove().getName());
	}
}
