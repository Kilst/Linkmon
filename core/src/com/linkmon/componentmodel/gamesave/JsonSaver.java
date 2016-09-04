package com.linkmon.componentmodel.gamesave;

import com.linkmon.componentmodel.World;

public class JsonSaver extends BaseSaver {

	public JsonSaver(ISaveEncryption encryptor) {
		super(encryptor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(World world) {
		// TODO Auto-generated method stub
		this.encryptSave("Save_File");
	}

	@Override
	public World load() {
		// TODO Auto-generated method stub
		this.decryptSave("Save_File");
		
		return null;
	}

}
