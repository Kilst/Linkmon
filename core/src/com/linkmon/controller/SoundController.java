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
	
	private float musicMaxVolume = 0.1f; // Current music volume
	private float soundMaxVolume = 0.1f; // Current sound volume
	
	private float fadeSpeed = 0.001f; // Fade speed
	
	private Music playingCurrently; // Current music
	private Music loadedCurrently; // Next track to play
	
	private Sound playingCurrentlySound; // Current sound
	private Sound loadedCurrentlySound; // Next sound to play
	
	private List<Music> musicList; // List to make sure everything gets disposed of
	private Music remove = null; // (basically a remove queue for musicList)
	
	public SoundController() {
		
		audioLoader =  Gdx.audio; // Create audioLoader
		
		musicList = new ArrayList<Music>(); // Create musicList
	}
	
	private void playSound(String filePath) {
		// Play a sound
		Sound sound = audioLoader.newSound(Gdx.files.internal(filePath)); // Load sound
		sound.play(soundMaxVolume); // Play sound at current volume
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
			// Fade-out current music
			try {
				// Check if volume not 0
				if(playingCurrently.getVolume() > 0f) {
					float volume = playingCurrently.getVolume() - fadeSpeed; // Get new faded volume
					if(volume < 0)
						volume = 0; // Going below 0 throws an exception when using music.play() again
					playingCurrently.setVolume(volume);  // Apply new faded volume
					return; // Break from the method until volume = 0
				}
				else { // Volume == 0
					// Stop track once it has faded
					musicList.remove(playingCurrently); // Remove from non-disposed list
					if(playingCurrently.isPlaying())
						playingCurrently.stop(); // Need to stop before disposing
					playingCurrently.dispose(); // Dispose of the track
					playingCurrently = loadedCurrently; // Set the current track
					loadedCurrently = null; // Null the loaded track variable
					return;
				}
			} catch (Exception e) {
				// Exception thrown if no track was playing (should only happen the first time, could use a flag)
				playingCurrently = loadedCurrently;
				loadedCurrently = null;
				return;
			}
			
		}
		// Fade-in the current track (Volume can be updated on the fly, so we need both checks
		if(playingCurrently != null ) {
			if(!playingCurrently.isPlaying()) // If the track isn't playing, play it
				playingCurrently.play();
			if(playingCurrently.getVolume() < musicMaxVolume){ // Check if volume != current maxVolume
				float volume = playingCurrently.getVolume() + fadeSpeed; // Add fade-in volume to volume
				if(volume > musicMaxVolume) // Check new volume isn't > musicMaxVolume
					volume = musicMaxVolume;
				playingCurrently.setVolume(volume); // Set new fade-in volume
			}
			// Fade-out the current track
			else if(playingCurrently.getVolume() > musicMaxVolume) {
				float volume = playingCurrently.getVolume() - fadeSpeed*10;
				if(volume < musicMaxVolume)
					volume = musicMaxVolume;
				playingCurrently.setVolume(volume);
			}
		}
	}
	
	private void updateSound() {
		// Check if a new sound has been loaded
		if(loadedCurrentlySound != null) {
			try {
				// Stop and dispose of the current sound (could probably fade, but no need at the moment)
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
		
		// Since we don't keep track of everything, I made a list to add music to, so I can check if it needs disposing.
		// Generally, this won't need calling, but in rare cases where the music fade doesn't keep up with the amount of loads,
		// we use this to dispose of anything that was set, but did not actually play.
		
		// Loop through list, checking for anything not playingCurrently or loadedCurrently. If it's neither, and still
		// in this list, it means it needs to be disposed of.
		for(Music music : musicList) {
			if(music != playingCurrently && music != loadedCurrently) {
				if(music.isPlaying())
					music.stop(); // Shouldn't need stopping, but just in case.
				music.dispose(); // Dispose of the track
				remove = music; // Add track to be removed from list
				break; // Break from the loop so we don't overwrite remove (It'll be called as many times as needed anyway)
			}
		}
		if(remove != null) { // If we disposed of a track, remove it from the list.
			musicList.remove(remove);
			remove = null; // Reset remove
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
	
	// Sound/Music is called via events
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
			case(ScreenEvents.PLAY_DING): {
				playSound(SoundFilePath.DING);
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
