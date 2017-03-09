package com.linkmon.model.libgdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.components.IRenderingComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;

public class LibgdxRenderingComponent implements IRenderingComponent {
	
	protected Sprite sprite;
	
	protected LibgdxAnimationComponent animation;

	protected boolean flipped;
	
	protected int id;
	
	public void setSprite(GameObject object) {
		sprite = new Sprite(ResourceLoader.getRegionFromId(object.getId()));
		object.setWidth(sprite.getWidth());
		object.setHeight(sprite.getHeight());
		
		sprite.setX(object.getX());
		sprite.setY(object.getY());
		
		this.id = object.getId();
	}
	
	public void removeSprite() {
		sprite = null;
	}
	
	public void setAnimation(LibgdxAnimationComponent animation) {
		this.animation = animation;
	}
	
	public LibgdxAnimationComponent getAnimationComponent() {
		return animation;
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
		if(sprite != null)
			updateSprite(object);
		if(animation!= null) {
			updateAnimation(object);
			animation.getCurrentAnimation();
		}
	}

	private void updateAnimation(GameObject object) {
		// TODO Auto-generated method stub
		animation.update(object);
		if(object.direction == Direction.RIGHT)
			flipped = (false);
		else if(object.direction == Direction.LEFT)
			flipped = (true);
		
		if(object.getId() != this.id) {
			animation.updateAnimations(object);
			this.id = object.getId();
		}
	}

	private void updateSprite(GameObject object) {
		// TODO Auto-generated method stub
		if(object.direction == Direction.RIGHT)
			sprite.setFlip(false, false);
		else if(object.direction == Direction.LEFT)
			sprite.setFlip(true, false);
		
		sprite.setX(object.getX());
		sprite.setY(object.getY());
		
		if(object.getId() != this.id) {
			setSprite(object);
			this.id = object.getId();
		}
	}

	public void draw(Batch batch, GameObject object) {
		// TODO Auto-generated method stub
		if(sprite != null)
			sprite.draw(batch);
//			batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getX()+(sprite.getWidth()/2),
//					sprite.getY()+(sprite.getHeight()/2), sprite.getWidth(), sprite.getHeight(), 1, 1, sprite.getRotation());
		else if(animation != null) {
			if(animation.getCurrentAnimation() != null) {
				if(flipped)
					batch.draw(animation.getCurrentKeyFrame(), object.getX(), object.getY(), animation.getCurrentKeyFrame().getRegionWidth(), animation.getCurrentKeyFrame().getRegionHeight());
				else
					batch.draw(animation.getCurrentKeyFrame(), object.getX()+animation.getCurrentKeyFrame().getRegionWidth(), object.getY(), -animation.getCurrentKeyFrame().getRegionWidth(), animation.getCurrentKeyFrame().getRegionHeight());	
			}
		}
	}

	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
//		if(sprite != null)
//			sprite.getTexture().dispose();
	}

}
