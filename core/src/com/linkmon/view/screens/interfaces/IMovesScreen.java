package com.linkmon.view.screens.interfaces;

public interface IMovesScreen extends MyScreen {
	public void setLinkmonMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy);
	public void setChoosableMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy);
}
