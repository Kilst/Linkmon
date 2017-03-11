package com.linkmon.view.screens.widgets;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.linkmon.MoveType;
import com.linkmon.view.screens.Move;

public class MoveTable extends Table {
	
	private int id;
	private boolean selected = false;
	
	private List<MoveTable> buttonList;
	
	private Move screen;
	
	private boolean isPlayerMove;
	
	public MoveTable(Skin skin2, int id, String name, int damage, int energy, int type, String effect, List<MoveTable> buttonList, Move screen, boolean isPlayerMove) {
		super();
		
		this.id = id;
		this.buttonList = buttonList;
		this.screen = screen;
		this.isPlayerMove = isPlayerMove;
		
		this.setBackground(skin2.getDrawable("attackMenuButton"));
		this.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
		this.setTouchable(Touchable.enabled);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("medium");
		
		Label name1 = new Label(name, labelStyle);
		Label pow1 = new Label("Power: " + damage, labelStyle);	
		Label energy1 = new Label("Energy: " + energy, labelStyle);
		Label type1 = new Label("Type: ", labelStyle);
		Label status1 = new Label("Effect: " + effect, labelStyle);
		
		Image type1Image = new Image();
		if(type == MoveType.FIRE) {
			type1Image.setDrawable(skin2.getDrawable("type-logo-fire"));
			type1Image.setSize(skin2.getDrawable("type-logo-fire").getMinWidth(), skin2.getDrawable("type-logo-fire").getMinHeight());
		}
		else if(type == MoveType.WATER) {
			type1Image.setDrawable(skin2.getDrawable("type-logo-water"));
			type1Image.setSize(skin2.getDrawable("type-logo-water").getMinWidth(), skin2.getDrawable("type-logo-water").getMinHeight());
		}
		else if(type == MoveType.GRASS) {
			type1Image.setDrawable(skin2.getDrawable("type-logo-grass"));
			type1Image.setSize(skin2.getDrawable("type-logo-grass").getMinWidth(), skin2.getDrawable("type-logo-grass").getMinHeight());
		}
		
		this.add(name1).align(Align.left).expand().padLeft(20).padTop(-15);
		this.add(type1).align(Align.right).padRight(20).padTop(-15);
		this.add(type1Image).padRight(20).size(skin2.getDrawable("type-logo-fire").getMinWidth(), skin2.getDrawable("type-logo-fire").getMinHeight()).padTop(-15);
		this.row();
		this.add(pow1).align(Align.left).padLeft(20).padTop(-20);
		this.add(energy1).align(Align.right).padRight(20).padTop(-20).colspan(2);
		this.row();
		this.add(status1).expandX().padTop(-20).colspan(3).padBottom(-15);
	}
	
	public void addMoveSwapListener() {
		this.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	setSelected(true);
	        	setColor(0, 1, 0, 1);
	        	if(isPlayerMove())
	        		getScreen().setPlayerMove(getId());
	        	else
	        		getScreen().setNewMove(getId());
	        	
	        	for(MoveTable move : getButtons()) {
	        		if(getId() != move.getId()) {
	        			move.setSelected(false);
	        			move.setColor(1, 1, 1, 1);
	        		}
	        	}
	        }
		});
	}
	
	public Move getScreen() {
		return screen;
	}
	
	public List<MoveTable> getButtons() {
		return buttonList;
	}

	public int getId() {
		return id;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isPlayerMove() {
		return isPlayerMove;
	}

	public void setPlayerMove(boolean isPlayerMove) {
		this.isPlayerMove = isPlayerMove;
	}

}
