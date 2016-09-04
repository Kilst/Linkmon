package com.linkmon.componentmodel.gamesave;

public interface ISaveEncryption {
	
	public String encrypt(String data);
	public String decrypt(String data);
}
