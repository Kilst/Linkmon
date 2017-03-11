package com.linkmon.controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.game.GameClass;
import com.linkmon.model.MService;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.LibgdxObjectFactory;
import com.linkmon.model.gameobject.ObjectFactory;

public class ControllerService {
	
	private MService mService;
	private LibgdxScreenController screenController;
	
	private PlayerController playerController;
	private LinkmonController linkmonController;
	private ShopController shopController;
	
	private NetworkController networkController;
	
	private WorldController worldController;
	
	private LibgdxInputController inputController;
	
	private LibgdxParticleController particleController;
	
	private LocalBattleController localBattleController;
	
	private MiniGameController miniGameController;
	
	private SoundController soundController;
	private SettingsController settingsController;
	
	public ControllerService(GameClass game, Group ui, EventManager eManager, SoundController sound, SettingsController settings) {
		
		ObjectFactory.init(new LibgdxObjectFactory(), eManager);
		
		this.settingsController = settings;
		
		mService = new MService(eManager);
		playerController = new PlayerController(mService.getPlayer());
		linkmonController = new LinkmonController(mService.getPlayer().getLinkmon());
		shopController = new ShopController(mService.getShop(), mService.getPlayer());
		worldController = new WorldController(mService.getWorld(), eManager);
		inputController = new LibgdxInputController(eManager, game);
		particleController = new LibgdxParticleController(eManager);
		
		networkController = new NetworkController(eManager, mService.getPlayer());
		
		localBattleController = new LocalBattleController(mService.getPlayer(), eManager, playerController, worldController);
		
		miniGameController = new MiniGameController(eManager, worldController);
		
		soundController = sound;
		
		eManager.addScreenListener(playerController);
		eManager.addScreenListener(linkmonController);
		eManager.addScreenListener(shopController);
		eManager.addScreenListener(worldController);
		eManager.addScreenListener(particleController);
		
		eManager.addScreenListener(networkController);
		
		eManager.addScreenListener(localBattleController);
		
		eManager.addScreenListener(soundController);
		
		screenController = new LibgdxScreenController(game, ui, eManager, this);
		
		eManager.addScreenListener(screenController);
	}
	
	public void update() {
		screenController.update();
		worldController.update();
		playerController.update();
		localBattleController.update();
		miniGameController.update();
		
		soundController.update();
	}
	
	public LibgdxParticleController getParticleController() {
		return particleController;
	}
	
	public LibgdxInputController getInputController() {
		return inputController;
	}

	public WorldController getWorldController() {
		// TODO Auto-generated method stub
		return worldController;
	}

	public void saveGame() {
		// TODO Auto-generated method stub
		mService.saveGame();
	}

	public void close() {
		// TODO Auto-generated method stub
		networkController.close();
	}

	public LocalBattleController getLocalBattleController() {
		// TODO Auto-generated method stub
		return localBattleController;
	}

	public PlayerController getPlayerController() {
		// TODO Auto-generated method stub
		return playerController;
	}

	public LinkmonController getLinkmonController() {
		// TODO Auto-generated method stub
		return linkmonController;
	}

	public SoundController getSoundController() {
		// TODO Auto-generated method stub
		return soundController;
	}
	
	public SettingsController getSettingsController() {
		// TODO Auto-generated method stub
		return settingsController;
	}

}
