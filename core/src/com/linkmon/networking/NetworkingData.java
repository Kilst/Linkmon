package com.linkmon.networking;

import com.linkmon.componentmodel.BattleLinkmon;

public class NetworkingData {
	
	private byte[] lastSentPacket;
	private byte[] currentSentPacket;
	
	private byte[] lastReceivedPacket;
	private byte[] currentReceivedPacket;
	
	private String lastServerMessage;
	private String currentServerMessage;
	
	private String serverWelcomeMessage;
	
	private BattleLinkmon myLinkmon; // Probably should create an "OnlineBattle" class for these like before. Except thought-out.
	private BattleLinkmon opponentLinkmon; // The class should be down in the model, and accessed through the NetworkController.
}
