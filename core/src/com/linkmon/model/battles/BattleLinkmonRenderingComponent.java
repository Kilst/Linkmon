package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.Timer;
import com.linkmon.model.components.IRenderingComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.view.particles.ParticleFactory;

public class BattleLinkmonRenderingComponent extends LibgdxRenderingComponent {
	
	private BitmapFont font;
	private String damage;
	private float damageX;
	private float damageY;
	
	private Sprite healthBarContainer;
	private Sprite healthBar;
	
	private float maxHealth = 0;
	private float health;
	
	private Sprite energyBarContainer;
	private Sprite energyBar;
	
	private Sprite targetPointer;
	
	private float maxEnergy = 50;
	private float energy;
	
	private static final double PERIOD = 60; // loop every 8 calls to updateNumber
	private static double SCALE = 10; // go between 0 and 800

	private int pos = 0;
	private int sineNum = 0;
	
	public float angle;
	
	private double gradient;
	
	private float moveX;
	private float moveY;
	private float yIntersect;
	
	float distance;
	
	private int direction;
	
	ParticleEffect effect;
	
	private boolean targeted;

	public void updateNumber() {
	    pos++;
	    sineNum = (int)(Math.sin(pos*2*Math.PI/PERIOD)*(SCALE/2)+(SCALE/2));
	}
	
	public void setAngle(float x, float y, GameObject linkmon)
	{
		direction = linkmon.direction;
		
		if(direction == Direction.RIGHT)
			yIntersect = linkmon.getY(); // Starts at the y-int
		else
			yIntersect = y - linkmon.getHeight()/2; // Runs until y-int. The y intersect has to be the target y
		
		double dx;
		// Minus to correct for coord re-mapping
		double dy;
		
	    dx = x - linkmon.getX() + linkmon.getWidth()/2;
	    dy = y - linkmon.getY()-linkmon.getHeight()/2;

	    double inRads = Math.atan2(dy,dx);

	    // We need to map to coord system when 0 degree is at 3 O'clock, 270 at 12 O'clock
	    if (inRads < 0)
	        inRads = Math.abs(inRads);
	    else
	        inRads = 2*Math.PI - inRads;

	    angle = (float)Math.toDegrees(inRads);
	    
	    gradient = (dy/dx);
	    
	    double grad = Math.tan(inRads);
	    
		dx = Math.pow(linkmon.getX() + linkmon.getWidth()/2 - x, 2);

	    dy = Math.pow(linkmon.getY()-linkmon.getHeight()/2 - y, 2);
	    
	    distance = (float) Math.sqrt(dx-dy);
	    
	    Gdx.app.log("BattleLinkmonRendering", "Angle: " + angle + "  Gradient: " + gradient + "  Gradient: " + grad + " Distance: " + distance);
	}
	
	public void setParticleEffect(int particleId, float x, float y) {
		
		
		effect = new ParticleEffect();
		ParticleFactory.getParticleFromId(particleId, effect);
		
		effect.setPosition(x, y);
		effect.start();
	}
	
	public BattleLinkmonRenderingComponent() {
		font = ResourceLoader.getLutFont("large");
		damage = null;
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		healthBarContainer = new Sprite(skin2.getRegion("healthBarContainer"));
		healthBar = new Sprite(skin2.getRegion("healthBarWhite"));
		healthBar.setColor(0, 1, 0, 1);
		
		energyBarContainer = new Sprite(skin2.getRegion("healthBarContainer"));
		energyBar = new Sprite(skin2.getRegion("healthBarWhite"));
		energyBar.setColor(0, 1, 0, 1);
		
		targetPointer = new Sprite(skin2.getRegion("pointer"));
		targetPointer.setFlip(false, true);
		
	}
	
	@Override
	public void update(GameObject linkmon) {
		// TODO Auto-generated method stub
		super.update(linkmon);
		
		if(maxHealth == 0) {
			maxHealth = ((BattleExtraComponent)linkmon.getExtraComponents()).getStats().getHealth();
			Gdx.app.log("BattleRenderer", "MaxHealth: " + maxHealth);
		}
		health = ((BattleExtraComponent)linkmon.getExtraComponents()).getStats().getHealth();
		
		energy = ((BattleExtraComponent)linkmon.getExtraComponents()).getEnergy();
		
		updateNumber();
		
		if(damage != null) {
			damageY+= 0.5f;
			damageX = sineNum;
			if(damageY > 50)
				damage = null;
		}
	}
	
	public void setDamage(String damage) {
		this.damage = damage;
		
		damageX = 0;
		damageY = 0;
	}
	
	@Override
	public void draw(Batch batch, GameObject object) {
		super.draw(batch, object);
		
		if(health/maxHealth < 0.15f)
			batch.setColor(1, 0, 0, 1);
		else if(health/maxHealth < 0.5f)
			batch.setColor(1, 1, 0.5f, 1);
		else
			batch.setColor(0, 1, 0, 1);
		
		batch.draw(healthBar, object.getX()+((healthBarContainer.getWidth()-object.getWidth())/2), object.getY()+object.getHeight()/1.2f, 150*(health/maxHealth), 20);
		
		batch.setColor(1, 1, 1, 1);
		batch.draw(healthBarContainer, object.getX()+((healthBarContainer.getWidth()-object.getWidth())/2), object.getY()+object.getHeight()/1.2f, 150, 20);
		
		batch.draw(energyBar, object.getX()+((energyBarContainer.getWidth()-object.getWidth())/2), object.getY()+object.getHeight()/1.2f-10, 150*(energy/maxEnergy), 10);
		batch.draw(energyBarContainer, object.getX()+((energyBarContainer.getWidth()-object.getWidth())/2), object.getY()+object.getHeight()/1.2f-10, 150, 10);
		
		if(targeted)
			batch.draw(targetPointer, object.getX()+(object.getWidth()/2)-(targetPointer.getWidth()/2), object.getY()+object.getHeight()+10+sineNum);
        if(damage != null) {
        	font.draw(batch, damage, object.getX()+(object.getWidth()/1.3f)+damageX, object.getY()+object.getHeight()/1.2f + damageY);
        }
        if(effect != null)
        	effect.draw(batch, Gdx.graphics.getDeltaTime());
	}

	public void setTargeted(boolean targeted) {
		// TODO Auto-generated method stub
		this.targeted = targeted;
	}

}
