package com.linkmon.componentmodel.gamesave;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.linkmon.componentmodel.Player;

public class JsonSaver extends BaseSaver {

	public JsonSaver(ISaveEncryption encryptor) {
		super(encryptor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save(Player player) {
		// TODO Auto-generated method stub
		
		// Get Objects
		GameSave save = new GameSave(player);
		
		
		byte[] encryptedBytes;
		
		// Create JSON file
		
		String jsonText = new String();
		Json json = new Json();
		
		jsonText = json.toJson(save);
		Gdx.app.log("SAVE GAME", jsonText);
		
		
		encryptedBytes = this.encryptSave(jsonText);
		
		FileHandle file = Gdx.files.local("playerSave.sav");
		
		file.writeBytes(encryptedBytes, false);
	}

	@Override
	public Player load() {
		// TODO Auto-generated method stub
		
		FileHandle file = Gdx.files.local("playerSave.sav");
		
		byte[] decryptedBytes = this.decryptSave(file.readBytes());
		String saveString = new String(decryptedBytes, StandardCharsets.UTF_8);
		Gdx.app.log("LOAD GAME", saveString);
		
		Json json = new Json();
		
		GameSave save = json.fromJson(GameSave.class, saveString);
		
		return save.getSavedPlayer();
	}
}
