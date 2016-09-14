package com.linkmon.view.screens.interfaces;

public interface IBattleView extends IBattleIntroView {

	public void updateHealths(int myHealth, int oppHealth);
	public void getMoves(int move1, int move2);
	public void updateOpponentMove(int oppMove);
	public void getSprites(int myLinkmonId, String name, int opponentLinkmonId, String opponentName);
}
