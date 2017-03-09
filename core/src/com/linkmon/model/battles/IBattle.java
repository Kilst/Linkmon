package com.linkmon.model.battles;

import com.linkmon.model.gameobject.GameObject;

public interface IBattle {
	
	public void setup(LocalBattleLinkmon[] myTeam, LocalBattleLinkmon[] oppTeam);
	public int calculateDamage(BattleExtraComponent battleComponent);
	public void getTarget(LocalBattleLinkmon linkmon);
	public void play();
	
	public void updateEnergy(BattleExtraComponent battleComponent);
	public void updateStatus(BattleExtraComponent battleComponent);
	
	public void setPlayerTarget(int target);
	public void setOpponentTarget(int move);
	public void setPlayerMove(int target);
	public void setOpponentMove(int move);
	
	public GameObject[] getLinkmonArray();
	public void updatePoison(BattleExtraComponent battleComponent);
	public void playTurn(LocalBattleLinkmon battleLinkmon);
	void updateDamage(BattleExtraComponent battleComponent);
	void updateHealing(BattleExtraComponent battleComponent);
	
	public void addManager(BattleManager battle);

	public BattleManager getBattleManager();
}
