package com.linkmon.model.items;

import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;

public class MedicineItemAction implements IItemAction {

	@Override
	public boolean use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().isSick()) {
			((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().setSick(false); // Cure linkmon
			return true; // True = use item
		}
		return false; // False = don't use item
	}

}
