package com.linkmon.model.gameobject.items;

import com.linkmon.model.gameobject.linkmon.StatType;

public class ItemFactory {
	
	public static Item getItemFromId(int id) {
		
		switch(id) {
			case(ItemIds.LARGE_MEAT) : {
				return new Food(ItemIds.LARGE_MEAT, "Large Meat", 1, 1, 100, 2);
			}
			case(ItemIds.SUPER_MEAT) : {
				return new StatFood(ItemIds.SUPER_MEAT, "Super Meat", 3000, 1, 100, 2, StatType.ATTACK, 5);
			}
			case(ItemIds.REVIVE_POTION) : {
				return new RevivePotion(ItemIds.REVIVE_POTION, "Revive Potion", 1000, 1);
			}
		}
		return null;
	}

}
