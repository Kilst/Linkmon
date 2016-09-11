package com.linkmon.view.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.FoodComponent;
import com.linkmon.componentmodel.items.ItemType;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.items.Food;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.model.gameobject.items.ItemIds;
import com.linkmon.model.gameobject.items.StatFood;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.IItems;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.widgets.ItemBox;
import com.linkmon.view.screens.widgets.ItemButton;

public class FeedWindow implements Screen, IPlayerItems {
	
	private Image backgroundImage;
	private Table container;
	private Table table;
	private Table tableFeed;
	private Table tableItems;
	private Group uiGroup;

	private ItemButton selectedButton;
	
	private ItemButton item;
	private List<ItemButton> buttonList;
	
	private Button backButton;
	private Button feedButton;
	private ItemBox itemBox;
	private Label itemText;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Skin skin;
	
	public FeedWindow(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		
		uiGroup = group;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		// Create Window Elements
		
		backgroundImage = new Image(skin2.getDrawable("feedBackground"));
		backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		container = new Table();
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Image image = new Image(new TextureRegion(new Texture(Gdx.files.internal("feedButton.png"))));
		
		table = new Table();
		
		tableFeed = new Table(skin);
		tableFeed.setBackground(skin2.getDrawable("statsTable"));
		
		tableItems = new Table(skin);
		tableItems.setBackground(skin2.getDrawable("statsTable"));
		
		TextureRegionDrawable back = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png"))));
		backButton = new ImageButton(back);
		
		TextureRegionDrawable feed = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("feedButtonGreen.png"))));
		feedButton = new ImageButton(feed);
		
		itemBox = new ItemBox();
		itemText = new Label("Item Name",skin);
		
		
		// Build Window Layout
		
		tableItems.add(itemBox).expand();
		tableItems.row();
		tableItems.add(itemText).expand();
		tableItems.row();
		tableItems.add(feedButton).expand().align(Align.bottom).pad(5*WorldRenderer.scaleXY);
		
		table.add(tableItems).width(200f*WorldRenderer.scaleXY).expandY().fill().padLeft(20*WorldRenderer.scaleXY).padRight(20*WorldRenderer.scaleXY);
		tableFeed.align(Align.top);
		table.add(tableFeed).expand().fill().padLeft(20*WorldRenderer.scaleXY).padRight(20*WorldRenderer.scaleXY);
		
		container.add(image);
		container.row();
		container.add(table).expand().fill();
		container.row();
		container.add(backButton).align(Align.right).size(128*WorldRenderer.scaleXY, 64*WorldRenderer.scaleXY);
		
		addListeners();
		
		tableItems.setVisible(true);
		
		
		buttonList = new ArrayList<ItemButton>();
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_ITEMS, ItemType.FOOD, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN_PREVIOUS));
            }
		});
		
		feedButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(selectedButton != null) {
            		eManager.notify(new ScreenEvent(ScreenEvents.FEED_LINKMON, selectedButton.getItemId()));
            		eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN_PREVIOUS));
            	}
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(backgroundImage);
		uiGroup.addActor(container);
		uiGroup.toFront();
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
		container.remove();
		backgroundImage.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayerItems(List<GameObject> items) {

	}

	@Override
	public void addPlayerItem(int id, String name, int quantity, int price, String itemText) {
		// TODO Auto-generated method stub
		item = new ItemButton(id, name, quantity, price, itemText, this);			
		buttonList.add(item);
		tableFeed.add(item).expandX().fillX();
		tableFeed.row();
	}

	@Override
	public void setSelectedItem(ItemButton selectedButton) {
		// TODO Auto-generated method stub
		
		for(ItemButton button : buttonList) {
			button.testSelected(selectedButton.getItemId());
		}
		
		this.selectedButton = selectedButton;

		itemBox.addItemImage(ResourceLoader.getItemRegionFromId(selectedButton.getItemId()).getTexture());
//		itemText.setFontScale(0.5f);
		itemText.setText(selectedButton.getItemName());
		tableItems.setVisible(true);
	}

}
