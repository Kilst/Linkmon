package com.linkmon.model.aonevonebattle;

import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectType;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonIds;

public class OpponentFactory {
	
	public static int[] getIds(int id) {
		int[] ids = new int[4];
		switch(id) {
			case(OpponentId.levelOne) : {
				ids[0] = OpponentId.levelOneOne;
				ids[1] = OpponentId.levelOneTwo;
				ids[2] = OpponentId.levelOneThree;
				ids[3] = OpponentId.levelOneFour;
				return ids;
			}
			case(OpponentId.levelTwo) : {
				ids[0] = OpponentId.levelTwoOne;
				ids[1] = OpponentId.levelTwoTwo;
				ids[2] = OpponentId.levelTwoThree;
				ids[3] = OpponentId.levelTwoFour;
				return ids;
			}
			case(OpponentId.levelThree) : {
				ids[0] = OpponentId.levelThreeOne;
				ids[1] = OpponentId.levelThreeTwo;
				ids[2] = OpponentId.levelThreeThree;
				ids[3] = OpponentId.levelThreeFour;
				return ids;
			}
		
		}
		return ids;
	}
	
	public static GameObject getOpponentFromId(int id) {
		
		GameObject opponent = null;
		
		switch(id) {
			case(OpponentId.levelOneOne) : {
				opponent = new GameObject(LinkmonIds.FIRE_BABY, ObjectType.LINKMON, null, null, null, new LinkmonExtraComponents());
				
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setHealth(100);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setAttack(10);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setDefense(10);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setSpeed(10);
				
				return opponent;
			}
			case(OpponentId.levelOneTwo) : {
				opponent = new GameObject(LinkmonIds.FIRE_BOY, ObjectType.LINKMON, null, null, null, new LinkmonExtraComponents());
				
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setHealth(200);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setAttack(22);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setDefense(16);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setSpeed(33);
				
				return opponent;
			}
			case(OpponentId.levelOneThree) : {
				opponent = new GameObject(LinkmonIds.FIRE_BABY, ObjectType.LINKMON, null, null, null, new LinkmonExtraComponents());
				
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setHealth(300);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setAttack(50);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setDefense(50);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setSpeed(29);
				
				return opponent;
			}
			case(OpponentId.levelOneFour) : {
				opponent = new GameObject(LinkmonIds.FIRE_BOY, ObjectType.LINKMON, null, null, null, new LinkmonExtraComponents());
				
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setHealth(500);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setAttack(80);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setDefense(60);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setSpeed(26);
				
				return opponent;
			}
			case(OpponentId.levelTwo) : {
				opponent = new GameObject(LinkmonIds.FIRE_BOY, ObjectType.LINKMON, null, null, null, new LinkmonExtraComponents());
				
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setHealth(500);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setAttack(85);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setDefense(15);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setSpeed(45);
				
				return opponent;
			}
			case(OpponentId.levelThree) : {
				opponent = new GameObject(LinkmonIds.FIRE_CHAMPION, ObjectType.LINKMON, null, null, null, new LinkmonExtraComponents());
				
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setHealth(400);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setAttack(120);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setDefense(220);
				((LinkmonExtraComponents)opponent.getExtraComponents()).getStats().setSpeed(180);
				
				return opponent;
			}
		}
		
		return opponent;
	}

}
