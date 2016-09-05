package com.linkmon.networking;

import com.linkmon.model.gameobject.linkmon.BattleLinkmon;

public interface INetworkService {
	
	public Client getClient();
	
	public void connect();
	
	public boolean disconnect();
	
	public void sendMove(int moveId);
	
	public void searchOpponent(BattleLinkmon linkmon);

	public void getMysteryGift();

	public void cancelSearch();
}
