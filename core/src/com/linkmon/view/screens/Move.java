package com.linkmon.view.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.LinkmonController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IMovesScreen;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.LocalMessageBox;
import com.linkmon.view.screens.widgets.MoveTable;
import com.linkmon.view.screens.widgets.SelectableMoveButton;

public class Move implements Screen, IMovesScreen {
	
	private Table rootTable;
	
	private Image darken;
	
	private Image heading;
	
	private Table topTable;
	private Table middleTable;
	private Table bottomTable;
	
	
	private ImageButton backButton;
	private ImageButton swapButton;
	
	private Group ui;
	
	private EventManager eManager;
	private List<MoveTable> linkmonButtonList;
	private List<MoveTable> choosableButtonList;
	private List<MoveTable> showMovesButtonList;
	
	private Table leftMiddleTable;
	
	private Table rightMiddleTable;
	private ScrollPane scrollPane;
	private Table scrollTable;
	
	Skin skin2;
	
	private int playerMoveId = -1;
	private int newMoveId = -1;
	
	private LinkmonController linkmonController;
	
	public Move(Group ui, EventManager eManager, LinkmonController linkmonController) {
		
		this.eManager = eManager;
		this.linkmonController = linkmonController;
		this.ui = ui;
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("large");
		
		linkmonButtonList = new ArrayList<MoveTable>();
		choosableButtonList = new ArrayList<MoveTable>();
		showMovesButtonList = new ArrayList<MoveTable>();
		
		Image heading = new Image(skin2.getDrawable("menuHeading"));
		
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
		
//		topTable.add(heading).width(heading.getWidth()).height(heading.getHeight()).expand().align(Align.left);
		
		Image moveImage = new Image(skin2.getDrawable("moveImage"));
		Image moveSelectImage = new Image(skin2.getDrawable("moveSelectImage"));
		topTable.add(moveImage).size(moveImage.getWidth(), moveImage.getHeight()).expand().align(Align.bottom);
		topTable.add(moveSelectImage).size(moveSelectImage.getWidth(), moveSelectImage.getHeight()).expand().align(Align.bottom);
		topTable.row();
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		
		backButton = new ImageButton(backButtonStyle);
		swapButton = new ImageButton(backButtonStyle);
		swapButton.setVisible(false);
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(1280, 720);
		darken.getColor().a = 0.7f;
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_MOVES, this));
		
		for(Table move : linkmonButtonList) {
			leftMiddleTable.add(move).expand().pad(5);
			leftMiddleTable.row();
		}
		
		scrollTable = new Table();
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		scrollPane = new ScrollPane(scrollTable, scrollStyle);
		scrollPane.setScrollingDisabled(true, false);
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_CHOOSABLE_MOVES, 1, this));
		
		setShowMovesList();
		
		rightMiddleTable.add(scrollPane);
		
		middleTable.add(leftMiddleTable).expandY().fillY().pad(20);
		middleTable.add(rightMiddleTable).expand().fill().pad(20).padTop(-5).padBottom(-5);
		
		
		
		bottomTable.add(backButton).width(backButton.getWidth()).height(backButton.getHeight()).expandX().align(Align.left);
		bottomTable.add(swapButton).width(swapButton.getWidth()).height(swapButton.getHeight()).expandX().align(Align.right);
		
		rootTable.add(topTable).size(1280, 110);
		rootTable.row();
		rootTable.add(middleTable).size(1280, 500);
		rootTable.row();
		rootTable.add(bottomTable).size(1280, 110);
		
		//rootTable.debug();
//		middleTable.debug();
//		move1Table.debug();
		
		addListeners();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		ui.addActor(darken);
		ui.addActor(rootTable);
	}

	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
	        }
		});
		
		swapButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	swapMove();
	        	setShowMovesList();
	        }
		});
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(newMoveId != -1 && playerMoveId != -1)
			swapButton.setVisible(true);
		else
			swapButton.setVisible(false);
		
		if(playerMoveId != -1)
			scrollPane.setVisible(true);
		else
			scrollPane.setVisible(false);
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
	public void setLinkmonMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy, String effect) {
		// TODO Auto-generated method stub
		
		linkmonButtonList.add(new MoveTable(skin2, id, name, damage, energy, type, effect, linkmonButtonList, this, true));
	}

	@Override
	public void setChoosableMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy, String effect) {
		// TODO Auto-generated method stub
		choosableButtonList.add(new MoveTable(skin2, id, name, damage, energy, type, effect, choosableButtonList, this, false));
	}
	
	private void setShowMovesList() {
		showMovesButtonList.clear();
		scrollTable.clearChildren();
		for(MoveTable move : choosableButtonList) {
			if(move.getId() != linkmonButtonList.get(0).getId()
					&& move.getId() != linkmonButtonList.get(1).getId()
					&& move.getId() != linkmonButtonList.get(2).getId())
				showMovesButtonList.add(move);
		}
		
		for(Table move : showMovesButtonList) {
			scrollTable.add(move).expand().pad(5);
			scrollTable.row();
		}
	}
	
	private void swapMove() {
		if(newMoveId != -1 && playerMoveId != -1) {
			linkmonController.swapMove(playerMoveId, newMoveId);
			new LocalMessageBox("Move Swap", "The moves were swapped.", ui);
			leftMiddleTable.clearChildren();
			linkmonButtonList.clear();
			eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_MOVES, this));
			for(Table move : linkmonButtonList) {
				leftMiddleTable.add(move).expand().pad(5);
				leftMiddleTable.row();
			}
			
			for(MoveTable move : choosableButtonList) {
    			move.setSelected(false);
    			move.setColor(1, 1, 1, 1);
        	}
			
			playerMoveId = -1;
			newMoveId = -1;
		}
	}
	
	public void setPlayerMove(int id) {
		// TODO Auto-generated method stub
		playerMoveId = id;
	}
	
	public void setNewMove(int id) {
		// TODO Auto-generated method stub
		newMoveId = id;
	}
}
