package com.linkmon.view.screens.interfaces;

import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.model.gameobject.items.Item;

public interface IPlayerItems extends IItems {
	public void setPlayerItems(List<GameObject> items);

	public void addPlayerItem(int id, String name, int quantity, int price, String itemText);
}
