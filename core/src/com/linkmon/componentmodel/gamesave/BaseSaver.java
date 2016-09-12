package com.linkmon.componentmodel.gamesave;

abstract class BaseSaver implements ISaveManager {
	
	private ISaveEncryption encryptor;
	
	public BaseSaver(ISaveEncryption encryptor) {
		this.encryptor = encryptor;
	}
	
	public byte[] encryptSave(String fileData) {
		return encryptor.encrypt(fileData);
	}
	
	public byte[] decryptSave(byte[] fileData) {
		return encryptor.decrypt(fileData);
	}

}
