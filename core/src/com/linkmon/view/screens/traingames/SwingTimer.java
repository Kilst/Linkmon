package com.linkmon.view.screens.traingames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.linkmon.helpers.ResourceLoader;

public class SwingTimer extends Table {
	
	private static final double PERIOD = 256; // loop every 8 calls to updateNumber
	private static double SCALE = 800; // go between 0 and 800

	private int pos = 0;
	private int sineNum = 0;
	private TextureRegion stick;
	
	private Table targetTable;
	
	private boolean finished = false;

	public void updateNumber() {
	    pos++;
	    sineNum = (int)(Math.sin(pos*2*Math.PI/PERIOD)*(SCALE/2)+(SCALE/2));
	}
	
	public int getSineNum() {
		finished = true;
		Gdx.app.log("SWING", "Clicked");
		return sineNum;
	}
	
	public SwingTimer(int width) {
		this.SCALE = width;
		this.setWidth(width);
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		this.setBackground(skin2.getDrawable("tableNoHeading"));
		stick = new TextureRegion(skin2.getRegion("pointer"));
		
		targetTable = new Table();
		targetTable.setBackground(skin2.getDrawable("tableNoHeading"));
		this.add(targetTable).width(100).expandY();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		
		if(sineNum > 350 && sineNum < 450)
			targetTable.setColor(0,1,0,1);
		else
			targetTable.setColor(1,1,1,1);
		
		batch.setColor(1,1,1,1);
		
		if(!finished)
			updateNumber();
		
		
		batch.draw(stick, getX()+sineNum-stick.getRegionWidth()/2, this.getY());
	}

}
