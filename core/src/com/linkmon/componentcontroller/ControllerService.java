package com.linkmon.componentcontroller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.linkmon.componentmodel.MService;
import com.linkmon.componentmodel.gameobject.LibgdxObjectFactory;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.game.GameClass;

public class ControllerService {
	
	private MService mService;
	private ScreenController screenController;
	
	private PlayerController playerContoller;
	private LinkmonController linkmonController;
	
	public ControllerService(GameClass game, Group ui, EventManager eManager) {
		
		ObjectFactory.init(new LibgdxObjectFactory());
		
		mService = new MService(eManager);
		playerContoller = new PlayerController(mService.getPlayer());
		linkmonController = new LinkmonController(mService.getPlayer().getLinkmon());
		screenController = new ScreenController(game, linkmonController, playerContoller, ui, eManager);
	}

	public MService getMService() {
		// TODO Auto-generated method stub
		return mService;
	}

}
