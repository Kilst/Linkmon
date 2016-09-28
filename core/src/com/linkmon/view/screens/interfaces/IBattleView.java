package com.linkmon.view.screens.interfaces;

public interface IBattleView extends IOnlineScreen {

	public void getMoves(int move1Id, String move1Name, int move2Id, String move2Name, int move3Id, String move3Name,
			int move4Id, String move4Name);
	public void getSprites(int myLinkmonId, int opponentLinkmonId);
	public void getStats(int myNewHealth, String name, int oppNewHealth, String opponentName);
	public void battleEnded();
	public void updateHealths(boolean first, int myNewHealth, int myEnergy, int oppNewHealth, int oppEnergy, String[][] messages);
	
	
}
