package com.linkmon.view.screens;

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
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
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
	
	private Table container;
	private Table innerContainer;
	private Table table;
	private Table tableLeft;
	private Table tableRight;
	private Group uiGroup;
	private ItemButton food;

	private Item selectedItem;
	
	private Button backButton;
	private Button feedButton;
	private ItemBox itemBox;
	private Label itemText;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Skin skin;
	
	private ScreenController screenController;
	
	private Image darken;
	
	public FeedWindow(Group group, ScreenController screenController, EventManager eManager) {
		this.eManager = eManager;
		this.screenController = screenController;
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.7f);
		
		uiGroup = group;
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		innerContainer = new Table();
		innerContainer.setBackground(skin2.getDrawable("container"));
		
		Image image = new Image(new TextureRegion(new Texture(Gdx.files.internal("feedButton.png"))));
		
		table = new Table();
		
		tableLeft = new Table(skin);
		tableLeft.setBackground(skin2.getDrawable("table"));
		tableLeft.align(Align.topLeft);
		
		tableRight = new Table(skin);
		tableRight.setBackground(skin2.getDrawable("table"));
		
		TextureRegionDrawable back = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png"))));
		backButton = new ImageButton(back);
		
		TextureRegionDrawable feed = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("feedButtonGreen.png"))));
		feedButton = new ImageButton(feed);
		
		itemBox = new ItemBox();
		
		itemText = new Label("Item Name",skin);
		
		tableRight.add(itemBox).expand().align(Align.top);
		tableRight.row();
		tableRight.add(itemText).expand();
		tableRight.row();
		tableRight.add(feedButton).expand().align(Align.bottom).padTop(5*WorldRenderer.scaleXY);
		
		table.add(tableRight).width(200f*WorldRenderer.scaleXY).expandY().fill().padLeft(20*WorldRenderer.scaleXY).padRight(20*WorldRenderer.scaleXY);
		table.add(tableLeft).expand().fill().padLeft(20*WorldRenderer.scaleXY).padRight(20*WorldRenderer.scaleXY);
		
//		table.debug();
//		tableLeft.debug();
//		tableRight.debug();
		
		innerContainer.add(image);
		innerContainer.row();
		innerContainer.add(table).expand().fill();
		innerContainer.row();
		innerContainer.add(backButton).align(Align.right).size(128*WorldRenderer.scaleXY, 64*WorldRenderer.scaleXY);
		container.add(innerContainer).size(Gdx.graphics.getWidth()/1.5f, Gdx.graphics.getHeight()/1.5f);
		addListeners();
		
		tableRight.setVisible(true);
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            }
		});
		
		feedButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(selectedItem != null) {
            		eManager.notify(new ControllerEvent(ControllerEvents.ITEM_USED, selectedItem));
            		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            	}
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		screenController.updateWindow(this);
		uiGroup.addActor(darken);
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
		darken.remove();
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedItem(Item item) {
		// TODO Auto-generated method stub
		selectedItem = item;
		itemBox.addItemImage(ResourceLoader.getItemRegionFromId(selectedItem.getId()).getTexture());
//		itemText.setFontScale(0.5f);
		itemText.setText(selectedItem.getName());
		tableRight.setVisible(true);
	}

	@Override
	public void getPlayerItems(List<Item> items) {
		Tree tree = new Tree(skin);
		// TODO Auto-generated method stub
		
		Node node;
		for(Item item : items) {
			if(item.getClass() == Food.class || item.getClass() == StatFood.class) {
				TextureRegion region = ResourceLoader.getItemRegionFromId(item.getId());
				food = new ItemButton(new TextureRegionDrawable(region), eManager, item, this);
				node = new Node(food);
				tree.add(node);
				//tableLeft.add(food).size(80*WorldRenderer.scaleXY, 80*WorldRenderer.scaleXY).pad(5*WorldRenderer.scaleXY);
				Gdx.app.log("FeedWindow", "Item quantity: " + item.getQuantity());
			}
		}
		tableLeft.add(tree).expand().fill();
	}

}
