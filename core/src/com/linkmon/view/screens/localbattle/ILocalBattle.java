package com.linkmon.view.screens.localbattle;

public interface ILocalBattle {
	
	public void updateHealths(int opp1, int opp2, int opp3, int p1, int p2, int p3);
	void getMoves(String move1Name, String move1Power, String move1Energy, int move1Id, String effect1,
			String move2Name, String move2Power, String move2Energy, int move2Id, String effect2, String move3Name,
			String move3Power, String move3Energy, int move3Id, String effect3);

}
