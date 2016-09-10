package com.linkmon.networking;

import com.linkmon.componentmodel.battles.BattleLinkmon;

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
	
	
	public byte[] getLastSentPacket() {
		return lastSentPacket;
	}
	public void setLastSentPacket(byte[] lastSentPacket) {
		this.lastSentPacket = lastSentPacket;
	}
	public byte[] getCurrentSentPacket() {
		return currentSentPacket;
	}
	public void setCurrentSentPacket(byte[] currentSentPacket) {
		setLastSentPacket(this.currentSentPacket);
		this.currentSentPacket = currentSentPacket;
	}
	public byte[] getLastReceivedPacket() {
		return lastReceivedPacket;
	}
	private void setLastReceivedPacket(byte[] lastReceivedPacket) {
		this.lastReceivedPacket = lastReceivedPacket;
	}
	public byte[] getCurrentReceivedPacket() {
		return currentReceivedPacket;
	}
	public void setCurrentReceivedPacket(byte[] currentReceivedPacket) {
		setLastReceivedPacket(this.currentReceivedPacket);
		this.currentReceivedPacket = currentReceivedPacket;
	}
	public String getServerWelcomeMessage() {
		return serverWelcomeMessage;
	}
	public void setServerWelcomeMessage(String serverWelcomeMessage) {
		this.serverWelcomeMessage = serverWelcomeMessage;
	}
}
