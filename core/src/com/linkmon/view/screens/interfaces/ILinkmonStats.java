package com.linkmon.view.screens.interfaces;

import com.linkmon.componentmodel.linkmon.BirthDate;

public interface ILinkmonStats extends MyScreen {
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank);
}
