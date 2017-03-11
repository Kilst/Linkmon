package com.linkmon.controller;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.linkmon.game.Settings;
import com.linkmon.model.Player;

public class SettingsController {
	
	Settings settings;
	
	private SoundController soundController;
	
	public SettingsController(SoundController soundController) {
		
		this.soundController = soundController;
		
		try {
			settings = load();
			soundController.setMusicVolume(settings.getMusicVolume());
			soundController.setSoundVolume(settings.getSoundVolume());
		} catch(Exception e) {
			settings = new Settings();
		}
		
	}
	
	public void setMusicVolume(float volume) {
		soundController.setMusicVolume(volume);
		settings.setMusicVolume(volume);
	}
	
	public void setSoundVolume(float volume) {
		soundController.setSoundVolume(volume);
		settings.setSoundVolume(volume);
	}
	
	public void save() {
		// TODO Auto-generated method stub
		
		// Create JSON file
		
		String jsonText = new String();
		Json json = new Json();
		
		jsonText = json.toJson(settings);
		Gdx.app.log("SAVE SETTINGS", jsonText);
		
		FileHandle file = Gdx.files.local("settings.sav");
		
		file.writeBytes(jsonText.getBytes(), false);
	}
	
	public Settings load() {
		// TODO Auto-generated method stub
		
		FileHandle file = Gdx.files.local("settings.sav");
		
		byte[] bytes = file.readBytes();
		String saveString = new String(bytes, StandardCharsets.UTF_8);
		Gdx.app.log("LOAD GAME", saveString);
		
		Json json = new Json();
		
		Settings settings = json.fromJson(Settings.class, saveString);
		
		return settings;
	}

	public float getSoundVolume() {
		// TODO Auto-generated method stub
		return soundController.getSoundVolume();
	}
	
	public float getMusicVolume() {
		// TODO Auto-generated method stub
		return soundController.getMusicVolume();
	}

}
