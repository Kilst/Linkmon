package com.linkmon.model.aonevonebattle;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.World;
import com.linkmon.model.battles.BattleWorld;
import com.linkmon.model.gameobject.GameObject;

public interface IBattles {
	
	public void setupBattle(GameObject player, GameObject opponent, int opponentId, World world, EventManager eManager);
	public void playRound();
	public void checkWin();
	public BattleWorld getWorld();
	public void update();
	
	

}
