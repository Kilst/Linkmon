package com.linkmon.game;

public class Settings {
	
	private float soundVolume;
	private float musicVolume;
	
	public Settings() {
		
	}
	
	
	public void setMusicVolume(float volume) {
		// TODO Auto-generated method stub
		musicVolume = volume;
	}
	
	public void setSoundVolume(float volume) {
		// TODO Auto-generated method stub
		soundVolume = volume;
	}


	public float getMusicVolume() {
		// TODO Auto-generated method stub
		return musicVolume;
	}
	
	public float getSoundVolume() {
		// TODO Auto-generated method stub
		return soundVolume;
	}

}
