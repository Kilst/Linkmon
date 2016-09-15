package com.linkmon.view.screens.interfaces;

public interface IBattleView extends IOnlineScreen {

	public void getMoves(int move1, int move2);
	public void getSprites(int myLinkmonId, int opponentLinkmonId);
	public void getStats(int myNewHealth, String name, int oppNewHealth, String opponentName);
	public void updateHealths(int myNewHealth, int oppNewHealth);
	public void battleEnded();
}
