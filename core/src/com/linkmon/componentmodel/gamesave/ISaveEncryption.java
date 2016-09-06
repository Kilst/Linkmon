package com.linkmon.componentmodel.gamesave;

public interface ISaveEncryption {
	
	public byte[] encrypt(String data);
	public byte[] decrypt(String data);
}
