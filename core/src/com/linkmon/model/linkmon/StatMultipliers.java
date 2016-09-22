package com.linkmon.model.linkmon;

public class StatMultipliers {

	public static final int FIRE_BABY = 0;
	public static final int FIRE_BOY = 1;
	public static final int FIRE_CHAMPION = 2;
	public static final int FIRE_MEGA = 3;
	public static final int FIRE_ULTIMATE = 4;
	
	
	public static float[] getMultipliers(int id) {
		float[] statMultipliers = new float[4];
		
		switch(id) {
			case(LinkmonIds.FIRE_BOY): {
				statMultipliers[0] = 2;
				statMultipliers[1] = 2;
				statMultipliers[2] = 2;
				statMultipliers[3] = 2;
				break;
			}
			case(LinkmonIds.FIRE_CHAMPION): {
				statMultipliers[0] = 1.2f;
				statMultipliers[1] = 1.4f;
				statMultipliers[2] = 1.1f;
				statMultipliers[3] = 3f;
				break;
			}
		}
		
		return statMultipliers;
	}
}
