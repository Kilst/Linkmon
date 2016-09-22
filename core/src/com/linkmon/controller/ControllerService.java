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
	
	public ControllerService(GameClass game, Group ui, EventManager eManager) {
		
		ObjectFactory.init(new LibgdxObjectFactory(), eManager);
		
		mService = new MService(eManager);
		playerController = new PlayerController(mService.getPlayer());
		linkmonController = new LinkmonController(mService.getPlayer().getLinkmon());
		shopController = new ShopController(mService.getShop(), mService.getPlayer());
		worldController = new WorldController(mService.getWorld());
		inputController = new LibgdxInputController(eManager);
		networkController = new NetworkController(eManager, mService.getPlayer());
		
		eManager.addScreenListener(playerController);
		eManager.addScreenListener(linkmonController);
		eManager.addScreenListener(shopController);
		eManager.addScreenListener(worldController);
		
		eManager.addScreenListener(networkController);
		
		screenController = new LibgdxScreenController(game, ui, eManager);
		
		eManager.addScreenListener(screenController);
	}
	
	public void update() {
		screenController.update();
		worldController.update();
		playerController.update();
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

}
