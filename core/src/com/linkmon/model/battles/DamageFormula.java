package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;

public class DamageFormula {
	
	private static final int damageMod = 6;
	
	public static int damage(int rank, int attack, int defense, int moveDamage, boolean defending) {
		// Pokemon damage formula (rank is level)
		double random = ((Math.random()*0.15)+0.85);
		int totalDamage = (int) ((rank*((float)attack/(float)defense)*moveDamage) * damageMod * random);
		
		if(defending)
			totalDamage = (int) (totalDamage / 2f);
		Gdx.app.log("DamageFormula", "Rank: " + rank + "  Random: " + random + "  Total Damage: " + totalDamage + "  Attack: " + attack + "  Defense: " + defense + "  Move Damage:" + moveDamage + "  Defending: " + defending);
		Gdx.app.log("DamageFormula","Total Damage: " + totalDamage);
		return totalDamage + 1;
		// ((Math.random()*0.15)+0.85))  ==  random 0.85 - 1.0
	}
	
	public static int poisonDamage(int rank, int tick, int baseDamage) {
		int poisonDamage = (int) (rank*((Math.sqrt(tick)))*baseDamage*(((Math.random()/10)*1.5)+0.85));
		return poisonDamage + 1;
	}
}
