package com.linkmon.view.screens.interfaces;

public interface IShop extends MyScreen {
	public void getPlayerGold(int gold);
	void addShopItem(int id, String name, int quantity, int price, int type, String itemText);
}
