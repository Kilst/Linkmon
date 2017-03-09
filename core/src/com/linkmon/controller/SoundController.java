package com.linkmon.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.SoundFilePath;

public class SoundController implements ModelListener, ScreenListener {

	private Audio audioLoader;
	
	private float musicMaxVolume = 0.1f;
	private float soundMaxVolume = 0.1f;
	
	private float fadeSpeed = 0.001f;
	
	private Music playingCurrently;
	private Music loadedCurrently;
	
	private Sound playingCurrentlySound;
	private Sound loadedCurrentlySound;
	
	private List<Music> musicList;
	private Music remove = null;
	
	public SoundController() {
		audioLoader =  Gdx.audio;
		
		musicList = new ArrayList<Music>();
	}
	
	private void playSound(String filePath) {
		Sound sound = audioLoader.newSound(Gdx.files.internal(filePath));
		sound.play(soundMaxVolume);
	}
	
	private void playMusic(String filePath) {
		Music music = audioLoader.newMusic(Gdx.files.internal(filePath)); // Load the music stream
		
		music.setVolume(0); // Volume set to 0 for fade in
		music.setLooping(true); // Loop the music
		
		musicList.add(music); // Add to a list. Used to check to for any dereferenced, non-disposed music
		
		loadedCurrently = music; // Sets the new track to be played in update
	}
	
	private void updateMusic() {
		// Check if new music has been loaded
		if(loadedCurrently != null) {
			try {
				if(playingCurrently.getVolume() > 0f) {
					float volume = playingCurrently.getVolume() - fadeSpeed; // Get new faded volume
					if(volume < 0)
						volume = 0; // Going below 0 throws an exception when using music.play() again
					playingCurrently.setVolume(volume);  // Apply new faded volume
					return; // Break from the method until volume = 0
				}
				else {
					// Stop track once it has faded
					musicList.remove(playingCurrently);
					if(playingCurrently.isPlaying())
						playingCurrently.stop(); // Need to stop before disposing
					playingCurrently.dispose(); // Dispose of the track
					playingCurrently = loadedCurrently; // Set the current track
					loadedCurrently = null; // Null loaded track
					return;
				}
			} catch (Exception e) {
				// Exception thrown if no track was playing (only happens the first time, should use a flag)
				playingCurrently = loadedCurrently;
				loadedCurrently = null;
				return;
			}
			
		}
		// Fade in the new track
		if(playingCurrently != null ) {
			
			if(!playingCurrently.isPlaying()) // If the track isn't playing, play it
				playingCurrently.play();
			if(playingCurrently.getVolume() < musicMaxVolume){
				float volume = playingCurrently.getVolume() + fadeSpeed;
				if(volume > 1)
					volume = 1;
				playingCurrently.setVolume(volume);
			}
			else if(playingCurrently.getVolume() > musicMaxVolume) {
				float volume = playingCurrently.getVolume() - fadeSpeed*10;
				if(volume < 0)
					volume = 0;
				playingCurrently.setVolume(volume);
			}
		}
	}
	
	private void updateSound() {
		// Check if a new sound has been loaded
		if(loadedCurrentlySound != null) {
			try {
				// Stop and dispose of the current sound
				playingCurrentlySound.stop();
				playingCurrentlySound.dispose();
				playingCurrentlySound = loadedCurrentlySound;
				loadedCurrentlySound = null;
				return;
			} catch (Exception e) {
				// Caught if no previous sound was playing
				playingCurrentlySound = loadedCurrentlySound;
				loadedCurrentlySound = null;
				return;
			}
			
		}
	}
	
	private void checkForNonDisposedMusic() {
		
		for(Music music : musicList) {
			if(music != playingCurrently && music != loadedCurrently) {
				if(music.isPlaying())
					music.stop();
				music.dispose();
				remove = music;
			}
		}
		if(remove != null) {
			musicList.remove(remove);
			remove = null;
		}
	}
	
	public void update() {
		updateMusic();
		updateSound();
		checkForNonDisposedMusic();
	}
	
	public void setMusicVolume(float volume) {
		musicMaxVolume = volume;
	}
	
	public void setSoundVolume(float volume) {
		soundMaxVolume = volume;
	}
	
	public float getSoundVolume() {
		return soundMaxVolume;
	}
	
	public float getMusicVolume() {
		return musicMaxVolume;
	}
	
	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.PLAY_MAIN_GAME_MUSIC): {
				playMusic(SoundFilePath.MAIN_GAME_MUSIC);
				return false;
			}
			case(ScreenEvents.PLAY_MENU_MUSIC): {
				playMusic(SoundFilePath.MENU_MUSIC);
				return false;
			}
			case(ScreenEvents.PLAY_BATTLE_MUSIC): {
				playMusic(SoundFilePath.BATTLE_MUSIC);
				return false;
			}
			case(ScreenEvents.PLAY_BUTTON_ACCEPT): {
				playSound(SoundFilePath.BUTTON_ACCEPT);
				return false;
			}
			case(ScreenEvents.PLAY_BUTTON_DECLINE): {
				playSound(SoundFilePath.BUTTON_DECLINE);
				return false;
			}
			case(ScreenEvents.PLAY_SHOP_MUSIC): {
				playMusic(SoundFilePath.SHOP_MUSIC);
				return false;
			}
			case(ScreenEvents.PLAY_THEME_MUSIC): {
				playMusic(SoundFilePath.THEME_MUSIC);
				return false;
			}
			case(ScreenEvents.PLAY_BATTLE_TOWER_MUSIC): {
				playMusic(SoundFilePath.BATTLE_TOWER_MUSIC);
				return false;
			}
		}
		return false;
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		
	}

}
