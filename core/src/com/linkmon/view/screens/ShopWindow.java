package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.items.ItemType;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IShop;
import com.linkmon.view.screens.widgets.ISelectable;
import com.linkmon.view.screens.widgets.ItemBox;
import com.linkmon.view.screens.widgets.SelectableItemButton;
import com.linkmon.view.screens.widgets.SelectionTable;

public class ShopWindow implements Screen, IShop, ModelListener {
	
	private Image img;
	private Table container;
	private SelectionTable tableItems;
	private Table buyTable;
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
	
	private int amount = 1;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Button addButton;
	private boolean addPress;
	private Button subtractButton;
	private boolean subtractPress;
	
	private long touchTime;
	
	private Skin skin;
	
	private Table heading;
	
	Label playerGold;
	Image coinsImage;
	String goldString = "Gold: ";
	
	public ShopWindow(Group group, EventManager eManager) {
		
		uiGroup = group;
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
		
		
		// Create Window Elements
		
		playerGold = new Label(goldString, skin);
		coinsImage = new Image(skin2.getDrawable("coins"));
		img = new Image(skin2.getDrawable("shopBackground"));
		img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		container = new Table();
		container.setBackground(skin2.getDrawable("newContainer"));
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		heading = new Table();
		heading.setBackground(skin2.getDrawable("title"));
		heading.setSize(250, 136);
		Label title = new Label("SHOP", skin);
		title.setFontScale(1.1f);
		
		tableItems = new SelectionTable();
		tableItems.setBackground(skin2.getDrawable("tableNoHeading"));
		tableItems.align(Align.topLeft);
		
		buyTable = new Table();
		buyTable.setBackground(skin2.getDrawable("tableNoHeading"));
		
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
		
		Table bottomTable = new Table();
		
		
		// Build Window Layout
		
		heading.add(title).padBottom(15);
		heading.setPosition((Gdx.graphics.getWidth()/2)-heading.getWidth()/2, Gdx.graphics.getHeight()-heading.getHeight());
		buyTable.add(itemBox).expand().align(Align.top).colspan(2).size(150, 150);
		buyTable.row();
		buyTable.add(itemText).expand().colspan(2);
		buyTable.row();
		buyTable.add(subtractButton).expandX().align(Align.right).padRight(5);
		buyTable.add(addButton).expandX().align(Align.left).padLeft(5);
		buyTable.row();
		buyTable.add(itemAmount).expand().colspan(2);
		buyTable.row();
		buyTable.add(itemPrice).expand().colspan(2);
		buyTable.row();
		buyTable.add(buyButton).expand().align(Align.bottom).colspan(2).padTop(5*UIRenderer.scaleXY);
		
		
		container.add(heading).colspan(3);
		container.row();
		container.add(buyTable).width(200f*UIRenderer.scaleXY).expandY().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		container.add(tableItems).expand().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY).colspan(2);
		container.row();
		
		bottomTable.add(playerGold).padLeft(15*UIRenderer.scaleXY);
		bottomTable.add(coinsImage).align(Align.left).padLeft(15*UIRenderer.scaleXY);
		bottomTable.add(backButton).align(Align.right).pad(5*UIRenderer.scaleXY).expandX();
		container.add(bottomTable).colspan(3).expandX().fillX();
		
		
		addListeners();
		
		// Get Shop Items and add them to Item Table
		eManager.notify(new ScreenEvent(ScreenEvents.GET_SHOP_ITEMS, ItemType.ALL, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MENU_SCREEN));
            }
		});
		
		buyButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(selectedItem != null) {
            		eManager.notify(new ScreenEvent(ScreenEvents.BUY_ITEM, ((SelectableItemButton)selectedItem).getItemId(), amount));
            		amount = 1;
            		itemAmount.setText(itemAmountString + amount);
            		itemPrice.setText(itemPriceString + ((SelectableItemButton)selectedItem).getPrice());
            		updateItemTable();
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
	
	private void updateBuyTable() {
		int direction = 0;
		if((subtractPress || addPress) && System.nanoTime() - touchTime > 1000000000 && selectedItem != null) {
			if(addPress)
				direction = 1;
			else if(subtractPress)
				direction = -1;
			if(selectedItem != null) {
        		amount += direction;
        		if(amount < 1)
            		amount = 1;
        		else if(amount > 99)
            		amount = 99;
        		itemAmount.setText(itemAmountString + amount);
            	itemPrice.setText(itemPriceString + amount*((SelectableItemButton)selectedItem).getPrice());
        	}
		}
		
		if(tableItems.isUpdated()) {
			selectedItem = tableItems.getSelectedItem();
			itemBox.addItemImage(ResourceLoader.getItemRegionFromId(((SelectableItemButton)selectedItem).getItemId()).getTexture());
			itemText.setText(((SelectableItemButton)selectedItem).getItemName());
			itemAmount.setText("Amount: 1");
			amount = 1;
			itemPrice.setText("Price: " + ((SelectableItemButton)selectedItem).getPrice());
			tableItems.setUpdated(false);
		}
	}
	
	private void updateItemTable() {
		
		tableItems.reset();
		eManager.notify(new ScreenEvent(ScreenEvents.GET_SHOP_ITEMS, ItemType.ALL, this));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(img);
		uiGroup.addActor(container);
		uiGroup.addActor(heading);
		uiGroup.addActor(backButton);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		updateBuyTable();
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
		container.remove();
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
