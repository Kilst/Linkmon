package com.linkmon.view.screens.interfaces;

import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.model.gameobject.items.Item;

public interface IShopItems extends IItems {
	public void getShopItems(List<GameObject> items);
}
