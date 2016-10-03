package com.linkmon.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.linkmon.controller.ControllerService;
import com.linkmon.controller.MiniGameController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.HelpMessages;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.view.LibgdxWorldRenderer;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.minigames.MiniGameUI;

public class GameClass extends Game implements ApplicationListener, ScreenListener {
	SpriteBatch batch;
	
	UIRenderer uiRenderer;
	
	GestureDetector gd;
	InputMultiplexer im;
	
	private EventManager eManager;
	
	private ControllerService controllerService;
	
	public MessageManager messages;
	
	private boolean saveLoaded = true;
	
	private HelpMessages help;
	
	private INotifications nHandler;
	
	private boolean notificationsSent = false;
	
	private LibgdxWorldRenderer worldRenderer;
	private LibgdxWorldRenderer miniGameRenderer;
	
	MiniGameController game;
	
	private ControllerService service;
	
	public OrthographicCamera camera;
	ExtendViewport viewport;
	
	public GameClass() {
		super();
	}
	
	public GameClass(INotifications nHandler) {
		super();
		// TODO Auto-generated constructor stub
		
		this.nHandler = nHandler;
	}

	@Override
	public void pause() {
		service.saveGame();
//		service.close();
//		GameSave.saveGame(controllerService.getPlayerController().getPlayer());
//		if(eManager != null)
//		eManager.notify(new ControllerEvent(ControllerEvents.SAVE_GAME));
//		if(!notificationsSent && controllerService != null) {
//			List<PushNotification> pushList = new ArrayList<PushNotification>();
//			pushList.add(NotificationCreator.getPushNotification(NotificationIds.POOP_NOTIFICATION, (LinkmonTimerLengths.POOP_SECONDS*TimerLengths.MILLI_FROM_SECOND) - (controllerService.getLinkmonController().getElapsedPoop()/TimerLengths.NANO_FROM_MILLI)));
//			pushList.add(NotificationCreator.getPushNotification(NotificationIds.HUNGRY_NOTIFICATION, (controllerService.getLinkmonController().getHungerLevel()*TimerLengths.MILLI_FROM_SECOND*LinkmonTimerLengths.HUNGER_SECONDS)));
//			pushList.add(NotificationCreator.getPushNotification(NotificationIds.DEAD_NOTIFICATION, 86400000000000L));
//			if(System.currentTimeMillis() - controllerService.getPlayerController().getGiftTime() < 1800000000000L);
//				pushList.add(NotificationCreator.getPushNotification(NotificationIds.MYSTERY_GIFT__NOTIFICATION, (controllerService.getPlayerController().getGiftTime()+1800000000000L)));
//			nHandler.sendNotification(pushList);
//			notificationsSent = true;
//			Gdx.app.log("NOTIFY", "Notification sent");
//		}
	}
	
	@Override
	public void resume() {
//		notificationsSent = false;
//		nHandler.clearNotification();
//		eManager.notify(new ControllerEvent(ControllerEvents.SAVE_GAME));
//		//GameSave.saveGame(player);
////		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.INTRO_SCREEN));
//		ResourceLoader.getInstance();
//		while (!ResourceLoader.assetManager.update())
//        {
//			
//        }
//		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
		
		System.gc();
		
		ResourceLoader.getInstance();
		while (!ResourceLoader.assetManager.update())
        {
			
        }
	}
	
	@Override
	public void create () {
		
//		notificationsSent = false;
//		nHandler.clearNotification();
		
		eManager = new EventManager();
		
		eManager.addScreenListener(this);
		
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ExtendViewport(480, 320, camera);
		
		ResourceLoader.getInstance();
		while (!ResourceLoader.assetManager.update())
        {
			
        }
		System.gc();
		saveLoaded = true;
		
		messages = new MessageManager(eManager);
		
		uiRenderer = new UIRenderer(messages, this, eManager);
		
		game = new MiniGameController(eManager);
		service = new ControllerService(this, uiRenderer.ui, eManager);
//		MiniGameController miniGame = new MiniGameController(eManager);
		worldRenderer = new LibgdxWorldRenderer(service.getWorldController().getWorld());
		
		uiRenderer.addParticleLoader(service.getParticleController().getUILoader());
		worldRenderer.addParticleLoader(service.getParticleController().getWorldLoader());
		
		im = new InputMultiplexer();
		
		im.addProcessor(uiRenderer.stage);
		im.addProcessor(service.getInputController().gd); // Controller added 2nd so ui clicks block world clicks
		
		Gdx.input.setInputProcessor(im);
	}

	@Override
	public void render () {
		
		camera.update();
		//batch.setProjectionMatrix(camera.projection);
		uiRenderer.stage.getBatch().setProjectionMatrix(camera.projection);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		service.update();
		
		batch.begin();
			if(miniGameRenderer != null) {
				miniGameRenderer.render(batch);
				game.update();
			}
			else
				worldRenderer.render(batch);
		batch.end();

		uiRenderer.render();
		this.getScreen().render(0);
	}
	
	@Override
	public void resize(int width, int height) {
		//uiRenderer.resize(width, height);
		//viewport.update(480, 320, true);
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.START_MINIGAME): {
				miniGameRenderer = new LibgdxWorldRenderer(game.getWorld());
				this.setScreen(new MiniGameUI(eManager, uiRenderer.ui));
				return false;
			}
		}
		return false;
	}
}
