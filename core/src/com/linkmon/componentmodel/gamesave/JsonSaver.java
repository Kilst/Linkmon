package com.linkmon.componentmodel.gamesave;

public class JsonSaver extends BaseSaver {

	public JsonSaver(ISaveEncryption encryptor) {
		super(encryptor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		this.encryptSave("Save_File");
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		this.decryptSave("Save_File");
	}

}
