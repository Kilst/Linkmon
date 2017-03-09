package com.linkmon.model.battles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.components.IRenderingComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.libgdx.LibgdxRenderingComponent;
import com.linkmon.view.shaders.ScrollShader;
import com.linkmon.view.shaders.WaveShader;

public class BeamAttackRenderingComponent extends LibgdxRenderingComponent {
	
	private WaveShader shader;
	private ScrollShader scrollShader;
	private Sprite beam;
	
	ParticleEffect effect;

	public BeamAttackRenderingComponent() {
		// TODO Auto-generated constructor stub
		shader = new WaveShader();
		scrollShader = new ScrollShader();
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		beam = new Sprite(skin2.getRegion("healthBarWhite"));
		beam.getTexture().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("Particles/rainbow.particles"), Gdx.files.internal("Particles/"));
	}
	
	public void setBeamPosition(GameObject object) {
			
		beam = new Sprite(ResourceLoader.getRegionFromId(ObjectId.ATTACK_BEAM_TAIL));
		beam.setColor(0, 1, 0, 1);
		beam.setSize(beam.getWidth()/2, beam.getHeight()/2);
			
			if(object.direction == Direction.RIGHT)
				beam.setX(object.getX()-(beam.getWidth()/2));
			else
				beam.setX(object.getX()+object.getWidth()/2-(beam.getWidth()/2));
			beam.setY(object.getY()+(object.getHeight()/2)-(beam.getHeight()/2));
			
			effect.setPosition(object.getX(), object.getY());
			effect.start();
			effect.getEmitters().first().getAngle().setHigh(-((MovePhysicsComponent)object.getPhysicsComponent()).getAngle() - 45);
			effect.getEmitters().first().getAngle().setHighMax(-((MovePhysicsComponent)object.getPhysicsComponent()).getAngle() + 45);
			effect.getEmitters().first().getAngle().setLow(-((MovePhysicsComponent)object.getPhysicsComponent()).getAngle());
		}
	
	@Override
    public void draw(Batch batch, GameObject object){
		
		
		batch.end();
//		scrollShader.updateScroll();
//		scrollShader.setShader(batch);
		batch.begin();
		
//		drawBeam(batch, object);

		
		batch.end();
		shader.updateWave();
		shader.setShader(batch);
		batch.enableBlending();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		batch.begin();
		
		//drawBeam(batch, object);

        // Reset any colour/alpha changes
        batch.setShader(null);
        batch.setColor(1, 1, 1, 1);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Default
        
        effect.draw(batch, Gdx.graphics.getDeltaTime());
    }

	@Override
	public void update(GameObject gameObject) {
		// TODO Auto-generated method stub
		
	}
	
	private void drawBeam(Batch batch, GameObject object) {
		double dx;
		// Minus to correct for coord re-mapping
		double dy;
		
		if(object.direction == Direction.RIGHT)
		    dx = Math.pow(beam.getX() - object.getX()-object.getWidth(), 2);
		else
			dx = Math.pow(beam.getX() - object.getX(), 2);

	    dy = Math.pow(beam.getY() - object.getY()-object.getHeight()/2, 2);
		    
		float distance = (float) Math.sqrt(dx-dy);
		
		batch.draw(beam, object.getX()+object.getWidth()/2, object.getY()+(object.getHeight()/2)-beam.getHeight()/2, (beam.getWidth()/2), (beam.getHeight()/2), -distance, beam.getHeight(), 1, 1, -((MovePhysicsComponent)object.getPhysicsComponent()).getAngle());
	}

}
