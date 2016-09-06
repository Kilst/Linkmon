package com.linkmon.componentmodel.gamesave;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.componentmodel.gameobject.ObjectId;
import com.linkmon.componentmodel.items.ItemComponent;
import com.linkmon.componentmodel.linkmon.BirthDate;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonStatsComponent;
import com.linkmon.componentmodel.linkmon.LinkmonStatusComponent;
import com.linkmon.componentmodel.linkmon.poop.PoopComponent;
import com.linkmon.helpers.TimerLengths;
import com.linkmon.model.gameobject.linkmon.LinkmonStats;
import com.linkmon.model.gameobject.linkmon.LinkmonTimerLengths;

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
//		Gdx.app.log("SAVE GAME", jsonText);
		
		while(jsonText.length()%16 != 0)
			jsonText+="/0";
		
		encryptedBytes = this.encryptSave(jsonText);
		
		FileHandle file = Gdx.files.local("playerSave.json");
		file.writeBytes(encryptedBytes, false);
	}

	@Override
	public Player load() {
		// TODO Auto-generated method stub
		
		FileHandle file = Gdx.files.local("playerSave.json");
		
		String playerJson = file.readString();
		while(playerJson.length()%16 != 0)
			playerJson+="/0";
		byte[] decryptedBytes = this.decryptSave(playerJson);
		String saveString = new String(decryptedBytes, StandardCharsets.UTF_8);
		Gdx.app.log("SAVE GAME", saveString);
		
		Json json = new Json();
		
		GameSave save = json.fromJson(GameSave.class, saveString);
		
		return save.getSavedPlayer();
	}
}
