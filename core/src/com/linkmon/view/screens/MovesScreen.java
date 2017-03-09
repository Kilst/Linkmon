package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.SmartFontGenerator;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IMovesScreen;
import com.linkmon.view.screens.widgets.ISelectable;
import com.linkmon.view.screens.widgets.SelectableItemButton;
import com.linkmon.view.screens.widgets.SelectableMoveButton;
import com.linkmon.view.screens.widgets.SelectionTable;

public class MovesScreen implements Screen, IMovesScreen {

	private Image backgroundImage;
	private Table rootTable;
	private Table table;
	private SelectionTable tableCurrentMoves;
	private SelectionTable tableSelectableMoves;
	private ScrollPane scrollPane;
	private Group uiGroup;

	private ISelectable selectedItem;
	
	private Button backButton;
	private Button swapButton;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Skin skin;
	
	MovesScreen screen = this;
	
	Table scrollTable;
	Table scrollTableCurrent;
	private ScrollPane scrollPaneCurrent;
	
	public MovesScreen(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		
		uiGroup = group;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("medium");
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		//scrollStyle.background = skin2.getDrawable("tableNoHeading");
		
		// Create Window Elements
		
		backgroundImage = new Image(skin2.getDrawable("statsBackground"));
		backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		rootTable = new Table();
		rootTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		rootTable.debug();
		
		Table heading = new Table();
		heading.setBackground(skin2.getDrawable("title"));
		heading.setSize(250, 136);
		Label title = new Label("MOVES", labelStyle);
//		title.setFontScale(1.1f);
		heading.add(title).padBottom(15);
		heading.setPosition((Gdx.graphics.getWidth()/2)-heading.getWidth()/2, Gdx.graphics.getHeight()-heading.getHeight());
		
		table = new Table();
		table.setBackground(skin2.getDrawable("newContainer"));
		
		table.debug();
		
		
		tableCurrentMoves = new SelectionTable();
		
		tableSelectableMoves = new SelectionTable();
		tableSelectableMoves.align(Align.top);
		
		backButton = new TextButton("Back", buttonStyle);
		
		swapButton = new TextButton("Swap", buttonStyle);
		
		scrollTableCurrent = new Table();
		scrollTableCurrent.setBackground(skin2.getDrawable("tableNoHeading"));
		SelectableMoveButton heading1 = new SelectableMoveButton();
		
		scrollPaneCurrent = new ScrollPane(tableCurrentMoves, scrollStyle);
		scrollPaneCurrent.setScrollingDisabled(true, false);
		
		scrollTable = new Table();
		scrollTable.setBackground(skin2.getDrawable("tableNoHeading"));
		SelectableMoveButton heading2 = new SelectableMoveButton();
		
		scrollPane = new ScrollPane(tableSelectableMoves, scrollStyle);
		scrollPane.setScrollingDisabled(true, false);

		
		// Build Window Layout
		
		table.add(heading).expandX().colspan(2).padTop(-100*UIRenderer.scaleX).size(250, 136*UIRenderer.scaleX);
		table.row();
		
		
		//table.add(tableCurrentMoves).expand().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		scrollTableCurrent.add(heading1).expandX();
		scrollTableCurrent.row();
		scrollTableCurrent.add(scrollPaneCurrent).expand();
		table.add(scrollTableCurrent).expand().fill().padLeft(20*UIRenderer.scaleX).padRight(20*UIRenderer.scaleX);
		
		if(Gdx.graphics.getWidth() <= 500)
			table.row();
		scrollTable.add(heading2).expandX();
		scrollTable.row();
		scrollTable.add(scrollPane).expand();
		table.add(scrollTable).expand().fill().padLeft(20*UIRenderer.scaleX).padRight(20*UIRenderer.scaleX);
		table.row();
		table.add(swapButton).expandX().colspan(2).padTop(20);
		table.row();
		table.add(backButton).expandX().colspan(2).padTop(20).padBottom(-55).padRight(-45).align(Align.bottomRight);
		
		//table.debug();
		
//		table.setOrigin(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
//		table.setTransform(true);
//		table.setScale(UIRenderer.scaleXY);
		
		rootTable.add(table).expand().fill();
		
		
		addListeners();
		
		// Get all Player items and add to tableItems
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_MOVES, this));
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
            	if(((SelectableMoveButton)tableCurrentMoves.getSelectedItem()) != null && 
            			((SelectableMoveButton)tableSelectableMoves.getSelectedItem()) != null) {
	            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_MOVE, ((SelectableMoveButton)tableCurrentMoves.getSelectedItem()).getMoveId(), ((SelectableMoveButton)tableSelectableMoves.getSelectedItem()).getMoveId()));
	            	tableCurrentMoves.reset();
	            	tableSelectableMoves.reset();
	            	eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_MOVES, screen));
            	}
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(backgroundImage);
		uiGroup.addActor(rootTable);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(tableCurrentMoves.isUpdated()) {
			tableSelectableMoves.reset();
			eManager.notify(new ScreenEvent(ScreenEvents.GET_CHOOSABLE_MOVES, ((SelectableMoveButton)tableCurrentMoves.getSelectedItem()).getSlot(), this));
			tableCurrentMoves.setUpdated(false);
		}
		
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
		backgroundImage.remove();
		rootTable.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLinkmonMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy, String effect) {
		// TODO Auto-generated method stub
		SelectableMoveButton item = new SelectableMoveButton(id, name, type, slot, damage, ignoreDamage, energy, tableCurrentMoves);
		tableCurrentMoves.addItem(item);
	}

	@Override
	public void setChoosableMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy, String effect) {
		// TODO Auto-generated method stub
		SelectableMoveButton item = new SelectableMoveButton(id, name, type, slot, damage, ignoreDamage, energy, tableSelectableMoves);
		tableSelectableMoves.addItem(item);
	}

}
