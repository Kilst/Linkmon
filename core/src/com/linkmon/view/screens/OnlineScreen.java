package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.networking.TcpService;
import com.linkmon.view.LinkmonSprite;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.INetworkScreen;
import com.linkmon.view.screens.interfaces.IPlayableLinkmons;
import com.linkmon.view.screens.widgets.LoadingWidget;

public class OnlineScreen implements Screen, ViewListener, IPlayableLinkmons, INetworkScreen {
	
	private Label serverWelcome;
	
	private String welcomeString;
	
	private Image darken;
	private Table container;
	private Group uiGroup;
	private Button connectButton;
	private Button giftButton;
	private Button backButton;
	private Skin skin;
	
	private Table table;
	
	private Table leftTable;
	private Table rightTable;
	
	private Table searchingTable;
	
	private SelectBox<BattleLinkmon> selectBox;

	private EventManager eManager;
	
	Skin skin2;
	
	LinkmonSprite linkmonImg;
	
	private boolean giftTimerEnded = false;
	
	public OnlineScreen(Group group, EventManager eManager) {
		uiGroup = group;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		this.eManager = eManager;
		eManager.addViewListener(this);
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
	}
	
	private void addListeners() {
		
		giftButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
//            	service = game.service;
//            	service.connect();
            	eManager.notify(new ControllerEvent(ControllerEvents.GET_MYSTERY_GIFT));
            	//eManager.notify(new ViewEvent(ViewEvents.CONNECT_TO_SERVER));
            	//game.setScreen(game.gameUi);
            }
		});
		
		connectButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	
	            	showSearch();
//	            	service = game.service;
//	            	service.connect();
	            	eManager.notify(new ControllerEvent(ControllerEvents.SEARCH_FOR_OPPONENT));
	            	//eManager.notify(new ViewEvent(ViewEvents.CONNECT_TO_SERVER));
	            	//game.setScreen(game.gameUi);
	            }
			});
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ControllerEvent(ControllerEvents.CLOSE_CONNECTION));
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.7f);
		
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("default-rect"));
		
		table = new Table(skin);	
		leftTable = new Table();
		rightTable = new Table();
		table.setBackground(skin2.getDrawable("container"));
		leftTable.setBackground(skin2.getDrawable("table"));
		rightTable.setBackground(skin2.getDrawable("table"));
		
		serverWelcome = new Label("", skin);
		
		table.add(serverWelcome).colspan(2);
		table.row();
		

		//statsLabel = new Label("Stats", skin);
		connectButton = new Button(skin2.getDrawable("battleButton"));
		giftButton = new Button(skin2.getDrawable("okayButton"));
		leftTable.add(connectButton).expand();
		leftTable.row();
		leftTable.add(giftButton).expand();
		//statsLabel = new Label("Stats", skin);
		
		
		
		
		
		table.add(leftTable).expand().fill().pad(15*WorldRenderer.scaleXY);
		table.add(rightTable).expand().fill().pad(15*WorldRenderer.scaleXY);
		table.row();
		
		backButton = new Button(skin2.getDrawable("backButton"));
		table.add(backButton).colspan(2).align(Align.bottomRight);
		
//		table.debug();
//		leftTable.debug();
//		rightTable.debug();
//		container.debug();
		
		container.add(table).size(Gdx.graphics.getWidth()/1.2f, Gdx.graphics.getHeight()/1.2f);
		
		uiGroup.addActor(darken);
		uiGroup.addActor(container);
		uiGroup.toFront();
		
		addListeners();
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_SERVER_WELCOME, this));
	}
	
	private void showSearch() {
		searchingTable = new Table();
		searchingTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		searchingTable.setBackground(skin.getDrawable("default-rect"));
		Label searching = new Label("Searching", skin);
		
		LoadingWidget load = new LoadingWidget();
		
		Button cancelButton = new Button(skin2.getDrawable("backButton"));
		
		cancelButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	searchingTable.remove();
            	eManager.notify(new ControllerEvent(ControllerEvents.CANCEL_SEARCH));
            }
		});
		
		
		searchingTable.add(searching).expand();
		searchingTable.row();
		searchingTable.add(load).expand().align(Align.bottom);
		searchingTable.row();
		searchingTable.add(cancelButton).expand().align(Align.bottom);
		
		
		uiGroup.addActor(searchingTable);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
//		Gdx.app.log("PacketHandler","Got Server Welcome: " + welcomeString);
//		if(serverWelcome.getText().toString() == "")
//			serverWelcome.setText(welcomeString);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if(searchingTable != null)
			searchingTable.remove();
		darken.remove();
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updatePlayableLinkmons(BattleLinkmon linkmon1, BattleLinkmon linkmon2) {
		// TODO Auto-generated method stub
		
		linkmonImg = new LinkmonSprite(linkmon1.getId());
		
		selectBox = new SelectBox<BattleLinkmon>(skin);
		Label selectLinkmon = new Label("Select Linkmon", skin);
		selectLinkmon.setFontScaleX(1.4f);
		selectLinkmon.setFontScaleY(1.4f);
		rightTable.add(selectLinkmon);
		rightTable.row();
		rightTable.add(linkmonImg);
		rightTable.row();
		rightTable.add(selectBox);
		
		BattleLinkmon[] array = new BattleLinkmon[2];
		array[0] = linkmon1;
		array[1] = linkmon2;
		
		selectBox.setItems(array);
		selectBox.showList();
		
		// Add listBox of linkmons to select
	}

	@Override
	public void onNotify(ViewEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ViewEvents.SERVER_WELCOME): {
//				this.welcomeString = event.str;
//				serverWelcome.setText(welcomeString);
//				Gdx.app.log("OnlineScreen","Got Server Welcome: " + welcomeString);
				break;
			}
			case(ViewEvents.UPDATE_GIFT_TIMER): {
				giftTimerEnded = event.status;
				break;
			}
		}
	}

	@Override
	public void setServerWelcome(String welcomeMessage) {
		// TODO Auto-generated method stub
		this.welcomeString = welcomeMessage;
		serverWelcome.setText(welcomeString);
		Gdx.app.log("OnlineScreen","Got Server Welcome: " + welcomeString);
	}
}
