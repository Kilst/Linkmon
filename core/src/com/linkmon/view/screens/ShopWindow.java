package com.linkmon.view.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.ItemComponent;
import com.linkmon.componentmodel.items.ItemType;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IShop;
import com.linkmon.view.screens.widgets.ISelectable;
import com.linkmon.view.screens.widgets.ItemBox;
import com.linkmon.view.screens.widgets.SelectableItemButton;
import com.linkmon.view.screens.widgets.SelectionTable;

public class ShopWindow implements Screen, IShop, ModelListener {
	
	private Image img;
	private Table innerContainer;
	private Table table;
	private SelectionTable tableItems;
	private Table tableRight;
	private Group uiGroup;
	private SelectableItemButton item;

	private ISelectable selectedItem;
	
	private Button backButton;
	private Button buyButton;
	private ItemBox itemBox;
	private Label itemText;
	private Label itemPrice;
	private String itemPriceString = "Price: ";
	private Label itemAmount;
	private String itemAmountString = "Amount: ";
	
	private int price = 0;
	private int amount = 1;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Button addButton;
	private boolean addPress;
	private Button subtractButton;
	private boolean subtractPress;
	private int direction = 1;
	
	private List<SelectableItemButton> buttonList;
	
	private long touchTime;
	
	private Skin skin;
	
	private Table heading;
	
	Label playerGold;
	Image coinsImage;
	String goldString = "Gold: ";
	
	public ShopWindow(Group group, EventManager eManager) {
		this.eManager = eManager;
		eManager.addModelListener(this);
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		playerGold = new Label(goldString, skin);
		coinsImage = new Image(skin2.getDrawable("coins"));
		
		uiGroup = group;
		img = new Image(skin2.getDrawable("shopBackground"));
		img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		innerContainer = new Table();
		innerContainer.setBackground(skin2.getDrawable("newContainer"));
		innerContainer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//innerContainer.debug();
		
		heading = new Table();
		heading.setBackground(skin2.getDrawable("title"));
		heading.setSize(250, 136);
		Label title = new Label("SHOP", skin);
		title.setFontScale(1.1f);
		heading.add(title).padBottom(15);
		heading.setPosition((Gdx.graphics.getWidth()/2)-heading.getWidth()/2, Gdx.graphics.getHeight()-heading.getHeight());
		
		table = new Table();
		
		tableItems = new SelectionTable();
		tableItems.setBackground(skin2.getDrawable("tableNoHeading"));
		tableItems.align(Align.topLeft);
		
		tableRight = new Table();
		tableRight.setBackground(skin2.getDrawable("tableNoHeading"));
		
		backButton = new TextButton("Back", buttonStyle);
		backButton.setPosition(Gdx.graphics.getWidth()-backButton.getWidth()-70, 55);
		
		buyButton = new TextButton("Buy", buttonStyle);
		
		addButton = new ImageButton(skin2.getDrawable("newAddButton"));
		subtractButton = new ImageButton(skin2.getDrawable("newSubtractButton"));
		
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
		tableRight.add(subtractButton).expandX().align(Align.right).padRight(5);
		tableRight.add(addButton).expandX().align(Align.left).padLeft(5);
		tableRight.row();
		tableRight.add(itemAmount).expand().colspan(2);
		tableRight.row();
		tableRight.add(itemPrice).expand().colspan(2);
		tableRight.row();
		tableRight.add(buyButton).expand().align(Align.bottom).colspan(2).padTop(5*UIRenderer.scaleXY);
		
		table.add(tableRight).width(200f*UIRenderer.scaleXY).expandY().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		table.add(tableItems).expand().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		
//		table.debug();
//		tableLeft.debug();
//		tableRight.debug();
		
		innerContainer.add(heading).colspan(3);
		innerContainer.row();
		innerContainer.add(tableRight).width(200f*UIRenderer.scaleXY).expandY().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		innerContainer.add(tableItems).expand().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY).colspan(2);
		innerContainer.row();
		Table bottomTable = new Table();
		bottomTable.add(playerGold).padLeft(15*UIRenderer.scaleXY);
		bottomTable.add(coinsImage).align(Align.left).padLeft(15*UIRenderer.scaleXY);
		bottomTable.add(backButton).align(Align.right).pad(5*UIRenderer.scaleXY).expandX();
		innerContainer.add(bottomTable).colspan(3).expandX().fillX();
		addListeners();
		
		tableRight.setVisible(true);
		
		
		buttonList = new ArrayList<SelectableItemButton>();
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_SHOP_ITEMS, ItemType.ALL, this));
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
            		eManager.notify(new ScreenEvent(ScreenEvents.BUY_ITEM, ((SelectableItemButton)selectedItem).getItemId(), amount));
            		amount = 1;
            		itemAmount.setText("Amount: " + amount);
            		itemPrice.setText("Price: " + ((SelectableItemButton)selectedItem).getPrice());
            	}
            }
		});
		
		addButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
            
            @Override 
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            	if(((SelectableItemButton)selectedItem)!= null) {
	            	addPress = true;
	            	touchTime = System.nanoTime();
	            	amount+=1;
	            	itemAmount.setText(itemAmountString + amount);
	            	itemPrice.setText(itemPriceString + amount*((SelectableItemButton)selectedItem).getPrice());
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
            	if(((SelectableItemButton)selectedItem)!= null) {
	            	subtractPress = true;
	            	amount-=1;
	            	if(amount < 1)
	            		amount = 1;
	            	itemAmount.setText(itemAmountString + amount);
	            	itemPrice.setText(itemPriceString + amount*((SelectableItemButton)selectedItem).getPrice());
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
		uiGroup.addActor(img);
		uiGroup.addActor(innerContainer);
		uiGroup.addActor(heading);
		uiGroup.addActor(backButton);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		if(tableItems.isUpdated()) {
			selectedItem = tableItems.getSelectedItem();
			itemBox.addItemImage(ResourceLoader.getItemRegionFromId(((SelectableItemButton)selectedItem).getItemId()).getTexture());
//			itemText.setFontScale(0.5f);
			itemText.setText(((SelectableItemButton)selectedItem).getItemName());
			itemAmount.setText("Amount: 1");
			
			itemPrice.setText("Price: " + ((SelectableItemButton)selectedItem).getPrice());
			tableItems.setUpdated(false);
		}
		
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
            	itemPrice.setText(itemPriceString + amount*((SelectableItemButton)selectedItem).getPrice());
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
		img.remove();
		innerContainer.remove();
		heading.remove();
		backButton.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addShopItem(int id, String name, int quantity, int price, String itemText) {
		// TODO Auto-generated method stub
		item = new SelectableItemButton(id, name, quantity, price, itemText, tableItems, uiGroup);
		tableItems.addItem(item);
	}

	@Override
	public void getPlayerGold(int gold) {
		// TODO Auto-generated method stub
		playerGold.setText(goldString+gold);
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ModelEvents.UPDATE_GOLD): {
				playerGold.setText("Gold: "+ event.value);
				break;
			}
		}
	}

}
