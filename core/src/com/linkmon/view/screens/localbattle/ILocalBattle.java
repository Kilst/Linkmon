package com.linkmon.view.screens.localbattle;

public interface ILocalBattle {
	
	public void updateHealths(int opp1, int opp2, int opp3, int p1, int p2, int p3);
	void getMoves(String move1Name, int move1Power, int move1Energy, int move1Id, int type1, String effect1,
			String move2Name, int move2Power, int move2Energy, int move2Id, int type2, String effect2, String move3Name,
			int move3Power, int move3Energy, int move3Id, int type3, String effect3);

}
