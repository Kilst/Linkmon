package com.linkmon.view.screens.interfaces;

public interface IBattleIntroView extends MyScreen {

//	public void getSprites(int myLinkmonId, int opponentLinkmonId);
	public void getOpponentStats(int health, int attack, int defense, int speed);
}
