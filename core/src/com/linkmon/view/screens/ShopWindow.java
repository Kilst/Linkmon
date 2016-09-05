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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.ItemComponent;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.IShopItems;
import com.linkmon.view.screens.widgets.ItemBox;
import com.linkmon.view.screens.widgets.ItemButton;

public class ShopWindow implements Screen, IShopItems {
	
	private Table container;
	private Table innerContainer;
	private Table table;
	private Table tableLeft;
	private Table tableRight;
	private Group uiGroup;
	private ItemButton item;

	private GameObject selectedItem;
	
	private Button backButton;
	private Button buyButton;
	private ItemBox itemBox;
	private Label itemText;
	private Label itemPrice;
	private String itemPriceString = "Price: ";
	private Label itemAmount;
	private String itemAmountString = "Amount: ";
	private int amount = 1;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Image darken;
	
	private Button addButton;
	private boolean addPress;
	private Button subtractButton;
	private boolean subtractPress;
	private int direction = 1;
	
	private long touchTime;
	
	public ShopWindow(Group group, EventManager eManager) {
		this.eManager = eManager;
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
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
		
		Image image = new Image(new TextureRegion(new Texture(Gdx.files.internal("shopButton.png"))));
		
		table = new Table();
		
		tableLeft = new Table(skin);
		tableLeft.setBackground(skin2.getDrawable("table"));
		tableLeft.align(Align.topLeft);
		
		tableRight = new Table(skin);
		tableRight.setBackground(skin2.getDrawable("table"));
		
		TextureRegionDrawable back = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png"))));
		backButton = new Button(back);
		
		TextureRegionDrawable feed = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("feedButtonGreen.png"))));
		buyButton = new ImageButton(feed);
		
		addButton = new ImageButton(skin2.getDrawable("addButton"));
		subtractButton = new ImageButton(skin2.getDrawable("subtractButton"));
		
		itemBox = new ItemBox();
		
		itemText = new Label("Placeholder",skin);
		itemText.setFontScale(1.2f, 1.2f);
		
		itemAmount = new Label("Amount: ",skin);
		itemAmount.setFontScale(1.2f, 1.2f);
		
		itemPrice  = new Label("Price: ",skin);
		itemPrice.setFontScale(1.2f, 1.2f);
		
		tableRight.add(itemBox).expand().align(Align.top).colspan(2);
		tableRight.row();
		tableRight.add(itemText).expand().colspan(2);
		tableRight.row();
		tableRight.add(subtractButton).size(60f*WorldRenderer.scaleXY, 60f*WorldRenderer.scaleXY).expandX().align(Align.right).padRight(15*WorldRenderer.scaleXY);
		tableRight.add(addButton).size(60f*WorldRenderer.scaleXY, 60f*WorldRenderer.scaleXY).expandX().align(Align.left).padLeft(15*WorldRenderer.scaleXY);
		tableRight.row();
		tableRight.add(itemAmount).expand().colspan(2);
		tableRight.row();
		tableRight.add(itemPrice).expand().colspan(2);
		tableRight.row();
		tableRight.add(buyButton).expand().align(Align.bottom).colspan(2).padTop(5*WorldRenderer.scaleXY);
		
		table.add(tableRight).width(200f*WorldRenderer.scaleXY).expandY().fill().padLeft(20*WorldRenderer.scaleXY).padRight(20*WorldRenderer.scaleXY);
		table.add(tableLeft).expand().fill().padLeft(20*WorldRenderer.scaleXY).padRight(20*WorldRenderer.scaleXY);
		
//		table.debug();
//		tableLeft.debug();
//		tableRight.debug();
		
		innerContainer.add(image);
		innerContainer.row();
		innerContainer.add(table).expand().fill();
		innerContainer.row();
		innerContainer.add(backButton).align(Align.right).pad(5*WorldRenderer.scaleXY);
		container.add(innerContainer).size(Gdx.graphics.getWidth()/1.2f*WorldRenderer.scaleXY, Gdx.graphics.getHeight()/1.2f*WorldRenderer.scaleXY);
		addListeners();
		
		tableRight.setVisible(true);
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_SHOP_ITEMS, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            }
		});
		
		buyButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(selectedItem != null) {
            		((ItemComponent)selectedItem.getExtraComponents()).setQuantity(amount);
            		//eManager.notify(new ControllerEvent(ControllerEvents.ITEM_BUY, selectedItem));
//            		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            		((ItemComponent)selectedItem.getExtraComponents()).setQuantity(1);
            		amount = 1;
            		itemAmount.setText("Amount: " + amount);
            		itemPrice.setText("Price: " + ((ItemComponent)selectedItem.getExtraComponents()).getPrice());
            	}
            }
		});
		
		addButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
            
            @Override 
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            	if(selectedItem!= null) {
	            	addPress = true;
	            	touchTime = System.nanoTime();
	            	amount+=1;
	            	itemAmount.setText(itemAmountString + amount);
	            	itemPrice.setText(itemPriceString + amount*((ItemComponent)selectedItem.getExtraComponents()).getPrice());
            	}
				return true;
            }
            @Override 
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            	addPress = false;
            }
		});
		
		subtractButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
            
            @Override 
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            	if(selectedItem!= null) {
	            	subtractPress = true;
	            	amount-=1;
	            	if(amount < 1)
	            		amount = 1;
	            	itemAmount.setText(itemAmountString + amount);
	            	itemPrice.setText(itemPriceString + amount*((ItemComponent)selectedItem.getExtraComponents()).getPrice());
	            	touchTime = System.nanoTime();
            	}
				return true;
            }
            @Override 
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            	subtractPress = false;
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(darken);
		uiGroup.addActor(container);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
//		Gdx.app.log("SHOPWINDOW", "Render");
		if((subtractPress || addPress) && System.nanoTime() - touchTime > 1000000000 && selectedItem != null) {
			if(addPress)
				direction = 1;
			else if(subtractPress)
				direction = -1;
			if(selectedItem != null) {
        		amount += direction;
        		if(amount < 1)
            		amount = 1;
        		itemAmount.setText(itemAmountString + amount);
            	itemPrice.setText(itemPriceString + amount*((ItemComponent)selectedItem.getExtraComponents()).getPrice());
        	}
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
		darken.remove();
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedItem(GameObject item) {
		// TODO Auto-generated method stub
		selectedItem = item;
		amount = 1;
		itemBox.addItemImage(ResourceLoader.getItemRegionFromId(selectedItem.getId()).getTexture());
//		itemText.setFontScale(0.5f);
		itemText.setText(selectedItem.getName());
		itemAmount.setText("Amount: " + amount);
		itemPrice.setText("Price: " + ((ItemComponent)selectedItem.getExtraComponents()).getPrice());
		tableRight.setVisible(true);
	}

	@Override
	public void getShopItems(List<GameObject> items) {
		// TODO Auto-generated method stub
		for(GameObject shopItem : items) {
			TextureRegion region = ResourceLoader.getItemRegionFromId(shopItem.getId());
			item = new ItemButton(new TextureRegionDrawable(region), eManager, shopItem, this);
			tableLeft.add(item).size(80*WorldRenderer.scaleXY, 80*WorldRenderer.scaleXY).pad(5*WorldRenderer.scaleXY);
		}
	}

}
