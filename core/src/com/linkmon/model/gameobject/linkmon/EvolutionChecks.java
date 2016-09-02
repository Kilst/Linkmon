package com.linkmon.model.gameobject.linkmon;

public class EvolutionChecks {
	
	public static int evolutionCheck(Linkmon linkmon) {
		
		switch(linkmon.getGrowthStage()) {
			case(GrowthStages.BABY) : {
				return babyCheck(linkmon);
			}
			case(GrowthStages.ROOKIE) : {
				return rookieCheck(linkmon);
			}
		}
		
		return linkmon.getId();
	}
	
	private static int babyCheck(Linkmon linkmon) {
		switch(linkmon.getId()) {
			case(LinkmonIds.FIRE_BABY) : {
				return EvolutionConditions.fireBoy(linkmon);
			}
		}
		return linkmon.getId();
	}
	
	private static int rookieCheck(Linkmon linkmon) {
		switch(linkmon.getId()) {
			case(LinkmonIds.FIRE_BOY) : {
				return EvolutionConditions.fireChampion(linkmon);
			}
		}
		return linkmon.getId();
	}

}
