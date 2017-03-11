package com.linkmon.view.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.LinkmonController;
import com.linkmon.controller.PlayerController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.HelpFlags;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.linkmon.BirthDate;
import com.linkmon.model.linkmon.RankIds;
import com.linkmon.model.linkmon.StatType;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IMovesScreen;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.LocalMessageBox;
import com.linkmon.view.screens.widgets.MoveTable;
import com.linkmon.view.screens.widgets.StatWidget;
import com.linkmon.view.screens.widgets.messages.ChatMessage;
import com.linkmon.view.screens.widgets.messages.LocalChatMessage;
import com.linkmon.view.screens.widgets.messages.MessageType;

public class CryoScreen implements Screen, ILinkmonStats, IMovesScreen {
	
	private Table rootTable;
	
	private Image darken;
	
	private Image heading;
	
	private Table topTable;
	private Table middleTable;
	private Table bottomTable;
	
	
	private ImageButton backButton;
	private ImageButton saveButton;
	
	private Group ui;
	
	private EventManager eManager;
	
	private Table leftMiddleTable;
	
	private Table rightMiddleTable;
	
	private Table statsTable;
	private Table linkmonTable;
	
	
	private Image linkmonRank;
	private Image rankLabel;
	private AnimationWidget anim;
	
	private Table movesTable;
	
	Skin skin2;
	Skin skin;
	
	private TextButton current;
	private TextButton slot1;
	private TextButton slot2;
	private TextButton slot3;
	private List<TextButton> slotList;
	
	private int checkedSlot;
	
	private Label moves;
	
	private PlayerController playerController;
	private LinkmonController linkmonController;
	
	private CryoScreen screen = this;
	
	LabelStyle labelStyle;
	
	Image moveImage;
	
	public CryoScreen(Group ui, EventManager eManager, PlayerController playerController, LinkmonController linkmonController) {
		
		this.eManager = eManager;
		this.playerController = playerController;
		this.linkmonController = linkmonController;
		this.ui = ui;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("large");
		
		Image heading = new Image(skin2.getDrawable("cryoTitle"));
		
		moveImage = new Image(skin2.getDrawable("moveImage"));
		
		rootTable = new Table();
		rootTable.setSize(1280, 720);
		rootTable.setBackground(new Image(ResourceLoader.assetManager.get("statsBackground.png", Texture.class)).getDrawable());
		
		topTable = new Table();
		topTable.setSize(1280, 135);
		
		middleTable = new Table();
		middleTable.setBackground(skin2.getDrawable("menuContainerPatch"));
		middleTable.setSize(1280, 500);
		
		leftMiddleTable = new Table();
		leftMiddleTable.setBackground(skin2.getDrawable("menuContainerInnerPatch"));
		
		rightMiddleTable = new Table();
		rightMiddleTable.setBackground(skin2.getDrawable("menuContainerInnerPatch"));
		
		
		bottomTable = new Table();
		bottomTable.setSize(1280, 135);
		
		statsTable = new Table();
		linkmonTable = new Table();
		movesTable = new Table();
		
		rankLabel = new Image(skin2.getDrawable("rankLabel"));
		linkmonRank = new Image(skin2.getDrawable("rank-s"));
		
		linkmonController.getLinkmonStats(screen);
		linkmonController.getLinkmonMoves(screen);
		
		topTable.add(heading).width(heading.getWidth()).height(heading.getHeight()).expand().align(Align.left);
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		
		ImageButtonStyle saveButtonStyle = new ImageButtonStyle();
		saveButtonStyle.down = skin2.getDrawable("saveButtonRed");
		saveButtonStyle.up = skin2.getDrawable("saveButtonGreen");
		
		saveButton = new ImageButton(saveButtonStyle);
		saveButton.setVisible(false);
		
		backButton = new ImageButton(backButtonStyle);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("blankButtonGreen");
		buttonStyle.down = skin2.getDrawable("blankButtonRed");
		buttonStyle.up = skin2.getDrawable("blankButton");
		buttonStyle.font = ResourceLoader.getLutFont("large");
		
		current = new TextButton("Current", buttonStyle);
		current.setChecked(true);
		slot1 = new TextButton("Slot 1", buttonStyle);
		slot2 = new TextButton("Slot 2", buttonStyle);
		slot3 = new TextButton("Slot 3", buttonStyle);
		
		slotList = new ArrayList<TextButton>();
		slotList.add(slot1);
		slotList.add(slot2);
		slotList.add(slot3);
		slotList.add(current);
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(1280, 720);
		darken.getColor().a = 0.7f;
		
		leftMiddleTable.add(current).padBottom(10);
		leftMiddleTable.row();
		leftMiddleTable.add(slot1);
		leftMiddleTable.row();
		leftMiddleTable.add(slot2);
		leftMiddleTable.row();
		leftMiddleTable.add(slot3);
		
		rightMiddleTable.add(statsTable);
		rightMiddleTable.add(linkmonTable).padLeft(100);
		rightMiddleTable.add(movesTable).expand();
		
		middleTable.add(leftMiddleTable).height(500-40).pad(10).align(Align.left);
		middleTable.add(rightMiddleTable).height(500-40).pad(10).expand().fill();
		
		
		
		bottomTable.add(backButton).width(backButton.getWidth()).height(backButton.getHeight()).expandX().align(Align.left);
		bottomTable.add(saveButton).width(saveButton.getWidth()).height(saveButton.getHeight()).expandX().align(Align.right);
		
		rootTable.add(topTable).size(1280, 110);
		rootTable.row();
		rootTable.add(middleTable).size(1280, 500);
		rootTable.row();
		rootTable.add(bottomTable).size(1280, 110);
		
		addListeners();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		ui.addActor(rootTable);
		
		if(playerController.checkHelpFlag(HelpFlags.CRYO_HELP)) {
			playerController.updateHelpFlag(HelpFlags.CRYO_HELP);
			String[] strings = new String[2];
			strings[0] = "Welcome to the Cryogenics Lab! Here you can save your current Linkmon for later use in battles.";
    		strings[1] = "When you save your Linkmon, their current stats and moves are also saved. These cannot be edited after saving, so make sure you save with the right moves.";
    		new LocalChatMessage(1, 0, strings, ui, eManager).show();
		}
		else {
			String[] strings = new String[2];
			strings[0] = "Welcome to the Cryogenics Lab!";
			strings[1] = "Looking to save some of your Linkmon's DNA?";
			new LocalChatMessage(1, 0, strings, ui, eManager).show();
		}
	}

	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
	        }
		});
		
		saveButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	
	        	
	        	// Check if slot is filled
	        	if(playerController.isCryoSlotFilled(checkedSlot)) {
	        		// Create new okay/cancel messagebox
	        		new LocalMessageBox(true, "Slot Not Empty", "The slot is currently filled.\n\nDo you wish to overwrite\nthe saved Linkmon?", ui){
		                // Override onOkay()
	        			@Override 
		                public void onOkay() {
	        				// Overwrite and display messagebox
		        			playerController.saveCurrentLinkmon(checkedSlot);
		        			new LocalMessageBox("Saved", "The slot was overwritten!", ui);
		        			// Reset to current Linkmon
		        			movesTable.clear();
		    	        	linkmonTable.clear();
		    	    		statsTable.clear();
		    	    		linkmonController.getLinkmonStats(screen);
		    	        	linkmonController.getLinkmonMoves(screen);
		    	        	resetChecked(current);
		    	        	
		    	        	current.setChecked(true);
		    	        	
		    	        	saveButton.setVisible(false);
		                }
		    		};

	        	}
	        	else {
	        		// Slot empty, just save
					playerController.saveCurrentLinkmon(checkedSlot);
					new LocalMessageBox("Saved", "Saved your current Linkmon!", ui);
					movesTable.clear();
		        	linkmonTable.clear();
		    		statsTable.clear();
		    		linkmonController.getLinkmonStats(screen);
		        	linkmonController.getLinkmonMoves(screen);
		        	resetChecked(current);
		        	
		        	current.setChecked(true);
		        	
		        	saveButton.setVisible(false);
	        	}
	        }
		});
		
		current.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	movesTable.clear();
	        	linkmonTable.clear();
	    		statsTable.clear();
	    		linkmonController.getLinkmonStats(screen);
	        	linkmonController.getLinkmonMoves(screen);
	        	
	        	resetChecked(current);
	        	
	        	saveButton.setVisible(false);
	        }
		});
		
		slot1.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	movesTable.clear();
	        	linkmonTable.clear();
	    		statsTable.clear();
	    		if(playerController.getCryoStats(screen, 0))
	        		playerController.getCryoMoves(screen, 0);
	    		else {
	        		Label label = new Label("No saved Linkmon", labelStyle);
	        		linkmonTable.add(label).padLeft(label.getWidth()/2);
	        	}
	    		
	    		resetChecked(slot1);
	    		
	    		saveButton.setVisible(true);
	    		
	    		checkedSlot = 0; // Used for cryoLinkmon array index
	        }
		});
		slot2.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	movesTable.clear();
	        	linkmonTable.clear();
	    		statsTable.clear();
	        	if(playerController.getCryoStats(screen, 1))
	        		playerController.getCryoMoves(screen, 1);
	        	else {
	        		Label label = new Label("No saved Linkmon", labelStyle);
	        		linkmonTable.add(label).padLeft(label.getWidth()/2);
	        	}
	        	
	        	resetChecked(slot2);
	        	
	        	saveButton.setVisible(true);
	        	
	        	checkedSlot = 1;
	        }
		});
		slot3.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	movesTable.clear();
	        	linkmonTable.clear();
	    		statsTable.clear();
	        	if(playerController.getCryoStats(screen, 2))
	        		playerController.getCryoMoves(screen, 2);
	        	else {
	        		Label label = new Label("No saved Linkmon", labelStyle);
	        		linkmonTable.add(label).padLeft(label.getWidth()/2);
	        	}
	        	
	        	resetChecked(slot3);
	        	
	        	saveButton.setVisible(true);
	        	
	        	checkedSlot = 2;
	        }
		});
	}
	
	private void resetChecked(TextButton currentChecked) {
		for(TextButton button : slotList) {
			if(currentChecked != button)
				button.setChecked(false);
			else
				button.setChecked(true);
		}
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
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
		rootTable.remove();
		darken.remove();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob,
			int rank) {
		// TODO Auto-generated method stub
		
		linkmonTable.clear();
		statsTable.clear();
		
		StatWidget widget = new StatWidget(skin, skin2, health, 999, StatType.HEALTH);
		statsTable.add(widget).pad(-10);
		statsTable.row();
		
		StatWidget widget2 = new StatWidget(skin, skin2, attack, 99,  StatType.ATTACK);
		statsTable.add(widget2).pad(-10);
		statsTable.row();
		
		StatWidget widget3 = new StatWidget(skin, skin2, defense, 99, StatType.DEFENSE);
		statsTable.add(widget3).pad(-10);
		statsTable.row();

		
		StatWidget widget4 = new StatWidget(skin, skin2, speed, 99, StatType.SPEED);
		statsTable.add(widget4).pad(-10);
		statsTable.row();
		
		anim = new AnimationWidget(id, 2f/76f);
		// TODO Auto-generated method stub
		switch(rank) {
			case(RankIds.E) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-e"));
				break;
			}
			case(RankIds.D) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-d"));
				break;
			}
			case(RankIds.C) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-c"));
				break;
			}
			case(RankIds.B) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-b"));
				break;
			}
			case(RankIds.A) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-a"));
				break;
			}
			case(RankIds.S) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-s"));
				break;
			}
		}
		
//		String minutes;
//		String hours;
//		
//		if(dob.getMinute() < 10)
//			minutes = "0"+dob.getMinute();
//		else
//			minutes = ""+dob.getMinute();
//		
//		if(dob.getHour() == 0)
//			hours = "12";
//		else if(dob.getHour() < 10)
//			hours = "0"+dob.getHour();
//		else
//			hours = ""+dob.getHour();
//		
//		this.dob.setText(dob.getDay()+"/"+dob.getMonth()+"/"+dob.getYear()+"  "+hours+":"+minutes);
		
		
		linkmonTable.add(anim).colspan(2).pad(5);
		linkmonTable.row();
		linkmonTable.add(rankLabel).expandX().align(Align.right);
		linkmonTable.add(linkmonRank).expandX().align(Align.left);
		
		movesTable.add(moveImage);
		movesTable.row();
	}

	@Override
	public void setLinkmonMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy, String effect) {
		// TODO Auto-generated method stub
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("medium");
		movesTable.add(new Label(name, labelStyle));
		movesTable.row();
	}

	@Override
	public void setChoosableMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy, String effect) {
		// TODO Auto-generated method stub
		
	}

}
