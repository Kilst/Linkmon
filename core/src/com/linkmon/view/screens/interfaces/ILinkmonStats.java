package com.linkmon.view.screens.interfaces;

import com.linkmon.model.gameobject.linkmon.BirthDate;

public interface ILinkmonStats {
	public void getLinkmonStats(int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank);
}
