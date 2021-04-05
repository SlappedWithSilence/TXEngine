package mckeken.item.effect.effects;

import mckeken.item.effect.Effect;
import mckeken.main.*;

/* Effect Functionality:
 * Number of properties: 2
 * Property Descriptions:
 * - 0: Health modifier. This is the quantity by which the player's health will change. May be positive or negative.
 * - 1: Level multiplier. This is the quantity by which property 0 will scale with the player's level. May only be positive.
 * - 2: Flat bonus. This is a static quantity that is added to the player's health. May be positive or negative.
*/ 


public class HealthEffect extends Effect {
	//*** Constants ***//
	final static int DEFAULT_HP_CHANGE = 10;
	

	// Default constructor.
	public HealthEffect() {
		super(new int[] {DEFAULT_HP_CHANGE, 1, 0}); // Configure the effect to have a maximum of 3 properties

	}

	public HealthEffect(int modifier, int multiplier, int bonus) {
		super(new int[] {modifier, multiplier, bonus});
	}

	public void perform() {
		Manager.player.modifyHealth( super.properties[0] * (Manager.player.getLevel() * super.properties[1]) + super.properties[2] );
	}

}