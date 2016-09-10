package com.linkmon.view.screens.interfaces;

import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.model.gameobject.items.Item;

public interface IShop extends IItems {
	public void getPlayerGold(int gold);
	void addShopItem(int id, String name, int quantity, int price, String itemText);
}
