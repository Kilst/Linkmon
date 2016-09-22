package com.linkmon.model.gamesave;

public interface ISaveEncryption {
	
	public byte[] encrypt(String data);
	public byte[] decrypt(byte[] data);
}
